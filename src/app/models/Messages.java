package app.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 9:02 PM
 */
public class Messages extends DAO {

    public static Message get(int id) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT * FROM messages " +
                "WHERE messages.id = ? ");
        st.setInt(1, id);

        ResultSet rs = st.executeQuery();
        if(rs.next()) {
            int from = rs.getInt("from");
            int to = rs.getInt("to");

            return new Message(id, Users.get(from), Users.get(to),
                    rs.getString("text"), rs.getTimestamp("date"), rs.getBoolean("read"));
        }
        return null;
    }

    public static boolean create(Message message) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "INSERT INTO messages (\"from\", \"to\", text, date) " +
                        "VALUES (?, ?, ?, ?) RETURNING id"
        );

        st.setInt(1, message.getFrom().getId());
        st.setInt(2, message.getTo().getId());

        st.setString(3, message.getText());
        st.setTimestamp(4, message.getDate());

        ResultSet rs = st.executeQuery();
        if(!rs.next())
            return false;

        message.setId(rs.getInt(1));
        return true;
    }

    public static List<Message> getConversation(User user1, User user2) throws SQLException {
        int id1 = user1.getId();
        int id2 = user2.getId();

        PreparedStatement st = connection.prepareStatement(
                "SELECT messages.* FROM messages " +
                        "WHERE \"from\" in (?, ?) AND \"to\" in (?, ?) " +
                        "ORDER BY date ASC "
        );

        st.setInt(1, id1);
        st.setInt(2, id2);

        st.setInt(3, id1);
        st.setInt(4, id2);

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
                "WHERE \"from\" = ? AND \"to\" = ?");

        st.setInt(1, thatUser.getId());
        st.setInt(2, thisUser.getId());
        return st.executeUpdate();
    }

    public static List<Conversation> getConversations(User user) throws SQLException {
        //TODO use unread_count field instead?
        PreparedStatement st = connection.prepareStatement(
                "SELECT messages.id mid, \"from\", \"to\", \"date\", \"read\", \"text\", users.*, " +
                        "(SELECT count(1) FROM messages m " +
                        "WHERE ARRAY[m.\"from\", m.\"to\"] @> ARRAY[messages.\"from\", messages.\"to\"] AND m.to = ? AND m.read = FALSE " +
                        ") as c" +
                        " FROM messages " +
                        "JOIN users on users.id in (\"from\", \"to\") AND users.id != ? " +
                        "where ? in (\"from\", \"to\") AND date = (SELECT max(date) FROM messages m\n" +
                        "WHERE ARRAY[m.\"from\", m.\"to\"] @> ARRAY[messages.\"from\", messages.\"to\"]) "
        );
        st.setInt(1, user.getId());
        st.setInt(2, user.getId());
        st.setInt(3, user.getId());

        ResultSet rs = st.executeQuery();

        List<Conversation> list = new ArrayList<>(rs.getFetchSize());

        while (rs.next()) {
            User user2 = Users.fromResultSet(rs);
            User from = user;
            User to = user2;
            if(from.getId() != rs.getInt("from")) {
                from = user2;
                to = user;
            }
            Message msg = new Message(rs.getInt("mid"), from, to, rs.getString("text"), rs.getTimestamp("date"), rs.getBoolean("read"));
            list.add(new Conversation(user, user2, rs.getInt("c"), msg));
        }

        return list;
    }

    public static int getUnreadCount(User user) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT count(1) FROM messages WHERE \"to\" = ? AND read = FALSE "
        );
        st.setInt(1, user.getId());

        ResultSet rs = st.executeQuery();
        if(rs.next())
            return rs.getInt(1);
        return 0;
    }


}
