package ru.kpfu.itis.group501.krylov.db1_it_project.servlets;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.CommonHelpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Message;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.NotFoundException;
import ru.kpfu.itis.group501.krylov.db1_it_project.models.Messages;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.User;

import javax.servlet.ServletException;
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
public class ChatServlet extends BaseServlet {

    private Messages messages = new Messages();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        String text = request.getParameter("text");
        int to = Integer.parseInt(request.getParameter("to"));

        try {
            chatService.sendMessage(CommonHelpers.getCurrentUser(request), to, text);
            Map<String, String> params = new HashMap<>();
            params.put("uid", String.valueOf(to));
            redirect(request, response, params);
        } catch (SQLException ignored) { }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int uid = Integer.parseInt(request.getParameter("uid"));

        try {
            User thisUser = CommonHelpers.getCurrentUser(request);
            User thatUser = userService.get(uid);

            List<Message> messages = chatService.getConversation(thisUser, thatUser);
            Map<String, Object> dataModel = new HashMap<>();

            dataModel.put("user1", thisUser);
            dataModel.put("user2", thatUser);
            dataModel.put("messages", messages);
            CommonHelpers.render(getServletContext(), request, response, "dialog.ftl", dataModel);

            this.messages.markRead(thisUser, thatUser);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            response.sendError(404);
        }
    }
}
