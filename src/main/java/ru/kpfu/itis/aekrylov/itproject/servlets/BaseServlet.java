package ru.kpfu.itis.aekrylov.itproject.servlets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.aekrylov.itproject.misc.QueryString;
import ru.kpfu.itis.aekrylov.itproject.misc.ParameterMap;
import ru.kpfu.itis.aekrylov.itproject.services.ChatService;
import ru.kpfu.itis.aekrylov.itproject.services.FeedbackService;
import ru.kpfu.itis.aekrylov.itproject.services.PostService;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

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

@Component
public abstract class BaseServlet extends HttpServlet {

    @Autowired
    protected UserService userService;
    @Autowired
    protected ChatService chatService;
    @Autowired
    protected PostService postService;
    @Autowired
    protected FeedbackService feedbackService;

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
