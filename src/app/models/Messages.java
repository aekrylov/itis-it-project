package app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 9:02 PM
 */
public class Messages {

    private static Connection connection = DB.getInstance().getConnection();
    private static Users users = new Users();

    public static Message get(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT * FROM messages " +
                "JOIN conversations on conversations.id = messages.conversation " +
                "WHERE messages.id = ? ");
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            int from = rs.getInt("from");
            int to = rs.getInt("user1");
            if(from == to)
                to = rs.getInt("user2");

            return new Message(id, users.get(from), users.get(to),
                    rs.getString("text"), rs.getTimestamp("date"), rs.getBoolean("read"));
        }
        return null;
    }

    public static boolean create(Message message) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO messages (conversation, \"from\", text, date) " +
                        "VALUES ((SELECT id FROM conversations WHERE user1 IN (?, ?) AND user2 in (?, ?) )," +
                        "?, ?, ?) RETURNING id"
        );

        st.setInt(1, message.getFrom().getId());
        st.setInt(2, message.getTo().getId());

        st.setInt(3, message.getFrom().getId());
        st.setInt(4, message.getTo().getId());

        st.setInt(5, message.getFrom().getId());
        st.setString(6, message.getText());
        st.setTimestamp(7, message.getTimestamp());

        ResultSet rs = st.executeQuery();
        if(!rs.next())
            return false;

        message.setId(rs.getInt(1));
        return true;
    }

    public static List<Message> getConversation(User user1, User user2) throws SQLException {
        int id1 = user1.getId();
        int id2 = user2.getId();
        if(id1 > id2) {
            id1 = user2.getId();
            id2 = user1.getId();
        }

        PreparedStatement st = connection.prepareStatement(
                "SELECT messages.* FROM conversations " +
                        "INNER JOIN messages on conversations.id = messages.conversation " +
                        "WHERE user1 = ? AND user2 = ? " +
                        "ORDER BY date ASC "
        );

        st.setInt(1, id1);
        st.setInt(2, id2);

        ResultSet rs = st.executeQuery();

        List<Message> list = new ArrayList<>(rs.getFetchSize());
        while (rs.next()) {
            User from = user1;
            User to = user2;
            if(rs.getInt("from") != from.getId()) {
                from = user2;
                to = user1;
            }
            list.add(new Message(rs.getInt("id"), from, to, rs.getString("text"), rs.getTimestamp("date"), rs.getBoolean("read")));
        }

        return list;
    }

    public static int markRead(User thisUser, User thatUser) throws SQLException {
        PreparedStatement st = connection.prepareStatement("" +
                "UPDATE messages SET read = TRUE " +
                "FROM conversations WHERE messages.conversation = conversations.id " +
                "AND ( ? in (user1, user2) AND ? in (user1, user2) AND messages.from = ? )");

        st.setInt(1, thisUser.getId());
        st.setInt(2, thatUser.getId());
        st.setInt(3, thatUser.getId());
        return st.executeUpdate();
    }

    public static List<Conversation> getConversations(User user) throws SQLException {
        //TODO use unread_count field instead?
        PreparedStatement st = connection.prepareStatement(
                "SELECT t.*, " +
                        "(SELECT count(1) FROM messages WHERE read = FALSE AND \"from\" != ? AND conversation = conv_id) as c, " +
                        "(SELECT messages.id FROM messages WHERE conversation = conv_id ORDER BY date DESC LIMIT 1) as mid " +
                        "FROM " +
                        "(SELECT conversations.id as conv_id, conversations.user1, conversations.user2, users.* from conversations " +
                        "JOIN users on (conversations.user1 = users.id AND user2 = ?) or " +
                        "(conversations.user2 = users.id AND user1 = ?) ) as t "
        );
        st.setInt(1, user.getId());
        st.setInt(2, user.getId());
        st.setInt(3, user.getId());

        ResultSet rs = st.executeQuery();

        List<Conversation> list = new ArrayList<>(rs.getFetchSize());

        while (rs.next()) {
            User user2 = Users.fromResultSet(rs);
            list.add(new Conversation(rs.getInt("conv_id"), user, user2, rs.getInt("c"), Messages.get(rs.getInt("mid"))));
        }

        return list;
    }

    public static int getUnreadCount(User user) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT count(1) FROM messages " +
                        "JOIN conversations ON messages.conversation = conversations.id " +
                        "WHERE ? in (conversations.user1, conversations.user2) AND \"from\" != ? " +
                        "AND messages.read = FALSE "
        );
        st.setInt(1, user.getId());
        st.setInt(2, user.getId());

        ResultSet rs = st.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        return 0;
    }


}
