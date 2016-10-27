package app.servlets;

import app.Helpers;
import app.models.Message;
import app.models.Messages;
import app.models.User;
import app.models.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 10:28 PM
 */
public class ChatServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //todo message sent
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int uid = Integer.parseInt(request.getParameter("uid"));

        try {
            User thisUser = Users.get(Helpers.getUsername(request));
            User thatUser = Users.get(uid);

            List<Message> messages = Messages.getConversation(thisUser, thatUser);
            Map<String, Object> dataModel = new HashMap<>();

            dataModel.put("user1", thisUser);
            dataModel.put("user2", thatUser);
            dataModel.put("messages", messages);
            Helpers.render(getServletContext(), request, response, "dialog.ftl", dataModel);

            Messages.markRead(thisUser, thatUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
