/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 7:24 PM
 * 11-501
 * Task
 */
package ru.kpfu.itis.group501.krylov.db1_it_project.servlets;

import ru.kpfu.itis.group501.krylov.db1_it_project.misc.QueryString;
import ru.kpfu.itis.group501.krylov.db1_it_project.misc.ParameterMap;
import ru.kpfu.itis.group501.krylov.db1_it_project.services.ChatService;
import ru.kpfu.itis.group501.krylov.db1_it_project.services.FeedbackService;
import ru.kpfu.itis.group501.krylov.db1_it_project.services.PostService;
import ru.kpfu.itis.group501.krylov.db1_it_project.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 7:24 PM
 * 11-501
 * Task 
 */
public abstract class BaseServlet extends HttpServlet {

    protected UserService userService = UserService.getInstance();
    protected ChatService chatService = ChatService.getInstance();
    protected PostService postService = PostService.getInstance();
    protected FeedbackService feedbackService = FeedbackService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
    }

    protected static void redirect(HttpServletRequest req, HttpServletResponse resp, Map<String, String> params)
            throws IOException {
        params.remove("password");
        params.remove("password_repeat");
        resp.sendRedirect(req.getServletPath() + QueryString.encodeStrings(params));
    }

    protected static void redirect(HttpServletRequest req, HttpServletResponse response) throws IOException {
        response.sendRedirect(req.getServletPath());
    }

    protected static ParameterMap getParameterMap(HttpServletRequest req) {
        Map<String, String[]> map = req.getParameterMap();
        ParameterMap result = new ParameterMap();
        for(Map.Entry<String, String[]> entry: map.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0]);
        }
        return result;
    }


}
