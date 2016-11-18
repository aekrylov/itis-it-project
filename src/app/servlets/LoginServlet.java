package app.servlets;

import app.Helpers;
import app.services.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 6:37 PM
 * 11-501
 * Login page
 */
public class LoginServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);
        Map<String, String> map = getParameterMap(request);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            if(!UserService.getInstance().checkCredentials(username, password)) {
                map.put("error", "password_invalid");
                redirect(request, response, map);
            }
            else {
                request.getSession().setAttribute("username", username);

                if(request.getParameter("remember") != null) {
                    Cookie userCookie = new Cookie("username", username);
                    userCookie.setMaxAge(60*60*24*7);
                    response.addCookie(userCookie);
                }
                response.sendRedirect("/");
            }
        } catch (NoSuchElementException e ) {
            map.put("error", "username_404");
            redirect(request, response, map);
        } catch (SQLException e) {
            e.printStackTrace();
            map.put("error", "db");
            redirect(request, response, map);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object username = request.getSession().getAttribute("username");
        if(username != null) {
            response.sendRedirect("/user");
            return;
        }

        String oldUsername = request.getParameter("username");
        if(oldUsername == null) oldUsername = "";

        String error = request.getParameter("error");

        Map<String, Object> data = new HashMap<>();
        data.put("username", oldUsername);
        if(error != null)
            data.put("error", Helpers.getErrorMessage(error));

        Helpers.render(getServletContext(), request, response, "login.ftl", data);
    }
}
