package app.servlets;

import app.Helpers;
import app.entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 11/15/16 6:53 PM
 */
public class SettingsServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        //display settings
        try {
            User user = Helpers.getCurrentUser(req);
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("user", user);

            Helpers.render(getServletContext(), req, resp, "settings.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        //todo process changes
    }
}
