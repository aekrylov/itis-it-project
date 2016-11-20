package ru.kpfu.itis.group501.krylov.db1_it_project.servlets;

import ru.kpfu.itis.group501.krylov.db1_it_project.Helpers;
import ru.kpfu.itis.group501.krylov.db1_it_project.entities.Conversation;

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
 * Date: 10/24/16 11:44 PM
 */
public class ChatListServlet extends BaseServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //TODO pagination
        try {
            List<Conversation> conversations = chatService.getConversations(Helpers.getCurrentUser(request));
            Map<String, Object> dataModel = new HashMap<>();

            dataModel.put("conversations", conversations);
            Helpers.render(getServletContext(), request, response, "messages.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
