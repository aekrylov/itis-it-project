package ru.kpfu.itis.aekrylov.itproject.models;

import ru.kpfu.itis.aekrylov.itproject.entities.Conversation;
import ru.kpfu.itis.aekrylov.itproject.entities.Message;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.ReflectiveHelpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 9:02 PM
 */
public class Messages extends DAO<Message> {

    public int markRead(User thisUser, User thatUser) throws SQLException {
        PreparedStatement st = connection.prepareStatement("" +
                "UPDATE messages SET read = TRUE " +
                "WHERE \"from\" = ? AND \"to\" = ?");

        st.setInt(1, thatUser.getId());
        st.setInt(2, thisUser.getId());
        return st.executeUpdate();
    }

    public List<Conversation> getConversations(User user) throws SQLException {
        PreparedStatement st = connection.prepareStatement(
                "SELECT messages.id mid, \"from\", \"to\", timestamp, \"read\", \"text\", users.*, " +
                        "(SELECT count(1) FROM messages m " +
                        "WHERE ARRAY[m.\"from\", m.\"to\"] @> ARRAY[messages.\"from\", messages.\"to\"] AND m.to = ? AND m.read = FALSE " +
                        ") as c" +
                        " FROM messages " +
                        "JOIN users on users.id in (\"from\", \"to\") AND users.id != ? " +
                        "where ? in (\"from\", \"to\") AND timestamp = (SELECT max(timestamp) FROM messages m\n" +
                        "WHERE ARRAY[m.\"from\", m.\"to\"] @> ARRAY[messages.\"from\", messages.\"to\"]) "
        );
        st.setInt(1, user.getId());
        st.setInt(2, user.getId());
        st.setInt(3, user.getId());

        ResultSet rs = st.executeQuery();

        List<Conversation> list = new ArrayList<>(rs.getFetchSize());

        while (rs.next()) {
            User user2 = ReflectiveHelpers.fromResultSet(rs, User.class);
            User from = user;
            User to = user2;
            if(from.getId() != rs.getInt("from")) {
                from = user2;
                to = user;
            }
            Message msg = new Message(rs.getInt("mid"), from, to, rs.getString("text"), rs.getTimestamp("timestamp"), rs.getBoolean("read"));
            list.add(new Conversation(user, user2, rs.getInt("c"), msg));
        }

        return list;
    }


}
