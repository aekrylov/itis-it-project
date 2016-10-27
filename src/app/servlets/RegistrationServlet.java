package app.servlets;

import app.Helpers;
import app.models.User;
import app.models.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/22/16 6:21 PM
 * 11-501
 * Registration
 */
public class RegistrationServlet extends BaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request, response);

        Map<String, String> params = getParameterMap(request);
        if(!params.get("password").equals(params.get("password_repeat"))) {
            params.put("error", "password_match");
        } else {
            try {
                if(Users.exists(params.get("username")))
                    params.put("error", "username_taken");
            } catch (SQLException e) {
                e.printStackTrace();
                params.put("error", "db");
            }
        }
        if(params.containsKey("error")) {
            redirect(request, response, params);
            return;
        }

        User user = new User(params.get("username"),
                Helpers.encrypt(params.get("password")),
                params.get("name"),
                params.get("email"));

        try {
            if(Users.create(user)) {
                Map<String, String> pass = new HashMap<>();
                pass.put("success", "1");
                pass.put("username", params.get("username"));
                redirect(request, response, pass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            params.put("error", "db");
            redirect(request, response, params);
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> dataModel = new HashMap<>();

        boolean success = request.getParameter("success") != null;
        if(success) {
            dataModel.put("username", request.getParameter("username"));

            response.sendRedirect("/");
            //TODO
/*
            Helpers.render(getServletContext(), response, "registration_success.ftl", dataModel);
*/
            return;
        }

        String errorCode = request.getParameter("error");
        if(errorCode != null) {
            dataModel.put("error", Helpers.getErrorMessage(errorCode));
            dataModel.putAll(getParameterMap(request));
        }

        Helpers.render(getServletContext(), request, response, "registration.ftl", dataModel);
    }
}
