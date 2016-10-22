package app.servlets;

import app.Helpers;
import app.models.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 6:37 PM
 * 11-501
 * Login page
 */
public class LoginServlet extends HttpServlet {
    private static Map<String, String> errors = new HashMap<>();
    static {
        errors.put("password", "Invalid password");
        errors.put("username", "User doesn't exist");
        errors.put("db", "Database error");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            //todo db
            String realPassword = Users.getPassword(username);
            if(realPassword == null) {
                response.sendRedirect("/login?error=username");
            }
            else if(password.equals(realPassword)) {
                request.getSession().setAttribute("username", username);

                Cookie userCookie = new Cookie("username", username);
                userCookie.setMaxAge(60*60*24*7);
                response.addCookie(userCookie);

                response.sendRedirect("/");
            } else {
                response.sendRedirect("/login?error=password&login="+username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("/login?error=db");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object username = request.getSession().getAttribute("username");
        if(username != null) {
            response.sendRedirect("/");
            return;
        }

        String oldUsername = request.getParameter("username");
        if(oldUsername == null) oldUsername = "";

        String error = request.getParameter("error");

        Map<String, String> data = new HashMap<>();
        data.put("username", oldUsername);
        if(error != null)
            data.put("error", errors.get(error));

        Helpers.render(getServletContext(), response, "login.ftl", data);
    }
}
