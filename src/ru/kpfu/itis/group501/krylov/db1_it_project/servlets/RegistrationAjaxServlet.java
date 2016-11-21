package ru.kpfu.itis.group501.krylov.db1_it_project.servlets;

import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInput;
import java.sql.SQLException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/21/16 5:20 PM
 */
public class RegistrationAjaxServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        JSONObject object = new JSONObject();
        String username = req.getParameter("username");
        try {
            object.put("available", !userService.exists(username));
            resp.getWriter().write(object.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
