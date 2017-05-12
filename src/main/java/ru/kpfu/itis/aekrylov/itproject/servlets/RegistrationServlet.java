package ru.kpfu.itis.aekrylov.itproject.servlets;

import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.misc.ParameterMap;
import ru.kpfu.itis.aekrylov.itproject.misc.ValidationException;

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

        ParameterMap params = getParameterMap(request);
        try {
            if(!params.get("password").equals(params.get("password_repeat"))) {
                params.put("error", "password_match");
            }
            else if(userService.exists(params.get("username"))) {
                params.put("error", "username_taken");
            }
            else if(userService.create(params)) {
                Map<String, String> pass = new HashMap<>();
                pass.put("success", "1");
                pass.put("username", params.get("username"));
                redirect(request, response, pass);
                return;
            }
            redirect(request, response, params);
        } catch (ValidationException e) {
            params.put("error", "validation");
            params.put("cause", e.getMessage());
            params.put("field", e.getField());
            redirect(request, response, params);
            return;
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
            response.sendRedirect("/");
            return;
        }

        String errorCode = request.getParameter("error");
        if(errorCode != null) {
            dataModel.putAll(getParameterMap(request));
            dataModel.put("error", CommonHelpers.getErrorMessage(errorCode));
        }

        CommonHelpers.render(getServletContext(), request, response, "registration.ftl", dataModel);
    }
}
