package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.misc.ParameterMap;
import ru.kpfu.itis.aekrylov.itproject.misc.ValidationException;
import ru.kpfu.itis.aekrylov.itproject.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 12:40 PM
 */
//@Controller("/register")
public class RegistrationController extends BaseServlet {

/*    @PostMapping
    protected void doPost(HttpServletRequest request, HttpServletResponse response, ModelMap params) throws ServletException, IOException {
        super.doPost(request, response);

        params.putAll(getParameterMap(request));
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
    }*/

    @GetMapping
    protected String doGet(HttpServletRequest request, ModelMap dataModel) throws ServletException, IOException {

        boolean success = request.getParameter("success") != null;
        if(success) {
            return "redirect:/";
        }

        String errorCode = request.getParameter("error");
        if(errorCode != null) {
            dataModel.putAll(getParameterMap(request));
            dataModel.put("error", CommonHelpers.getErrorMessage(errorCode));
        }
        return "registration";
    }

}
