package ru.kpfu.itis.aekrylov.itproject.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 6:57 PM
 * 11-501
 * Task 
 */
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().removeAttribute("username");

        Cookie replacement = new Cookie("username", "");
        replacement.setMaxAge(0);
        resp.addCookie(replacement);

        resp.sendRedirect("/login");

    }
}
