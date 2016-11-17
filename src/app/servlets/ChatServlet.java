package app.servlets;

import app.Helpers;
import app.models.Message;
import app.models.Messages;
import app.models.User;
import app.models.Users;
import app.services.ChatService;
import app.services.UserService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/25/16 10:28 PM
 */
public class ChatServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        String text = request.getParameter("text");
        int to = Integer.parseInt(request.getParameter("to"));

        JSONObject object = new JSONObject();
        try {
            Message message = new Message(Helpers.getCurrentUser(request), Users.get(to),
                    text, Timestamp.from(Instant.now()));

            String status = Messages.create(message) ? "OK" : "ERROR";
            object.put("status", status);
        } catch (SQLException e) {
            e.printStackTrace();
            object.put("error", e.getMessage());
        } finally {
            response.getWriter().print(object);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int uid = Integer.parseInt(request.getParameter("uid"));

        try {
            User thisUser = Helpers.getCurrentUser(request);
            User thatUser = UserService.getInstance().get(uid);

            List<Message> messages = ChatService.getInstance().getConversation(thisUser, thatUser);
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
