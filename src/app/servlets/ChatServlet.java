package app.servlets;

import app.Helpers;
import app.entities.Message;
import app.misc.NotFoundException;
import app.models.Messages;
import app.entities.User;
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

    private Messages messages = new Messages();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        String text = request.getParameter("text");
        int to = Integer.parseInt(request.getParameter("to"));

        JSONObject object = new JSONObject();
        try {
            String status = chatService.sendMessage(Helpers.getCurrentUser(request), to, text) ? "OK" : "ERROR";
            object.put("status", status);
        } catch (SQLException e) {
            e.printStackTrace();
            object.put("error", e.getMessage());
        } finally {
            //response.getWriter().print(object);
            Map<String, String> params = new HashMap<>();
            params.put("uid", String.valueOf(to));
            redirect(request, response, params);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int uid = Integer.parseInt(request.getParameter("uid"));

        try {
            User thisUser = Helpers.getCurrentUser(request);
            User thatUser = userService.get(uid);

            List<Message> messages = chatService.getConversation(thisUser, thatUser);
            Map<String, Object> dataModel = new HashMap<>();

            dataModel.put("user1", thisUser);
            dataModel.put("user2", thatUser);
            dataModel.put("messages", messages);
            Helpers.render(getServletContext(), request, response, "dialog.ftl", dataModel);

            this.messages.markRead(thisUser, thatUser);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            response.sendError(404);
        }
    }
}
