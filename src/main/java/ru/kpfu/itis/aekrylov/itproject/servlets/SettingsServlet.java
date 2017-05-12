package ru.kpfu.itis.aekrylov.itproject.servlets;

import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
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
 * Date: 11/15/16 6:53 PM
 */
public class SettingsServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        //display settings
        try {
            User user = CommonHelpers.getCurrentUser();
            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("user", user);

            CommonHelpers.render(getServletContext(), req, resp, "settings.ftl", dataModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        ParameterMap params = getParameterMap(req);

        String act =  params.get("act");

        Map<String, String> pass = new HashMap<>();
        try {
            User user = CommonHelpers.getCurrentUser();
            //user is never null due to web app structure
            assert user != null;
            switch (act) {
                case "info":
                    if(params.get("username") != null) user.setUsername(params.get("username"));
                    if(params.get("name") != null) user.setName(params.get("name"));
                    if(params.get("email") != null) user.setEmail(params.get("email"));
                    user.validate(false);

                    userService.updateInfo(user);
                    break;

                case "password":
                    String old = params.get("password_old");
                    String new1 = params.get("password_new1");
                    String new2 = params.get("password_new2");

                    if(!user.getPassword().equals(CommonHelpers.encrypt(old))) {
                        pass.put("error", CommonHelpers.getErrorMessage("password_invalid"));
                    } else if(new1 == null || !new1.equals(new2)) {
                        pass.put("error", CommonHelpers.getErrorMessage("password_match"));
                    } else {
                        userService.updatePassword(user, new1);
                    }
                    break;
            }
        } catch (SQLException e) {
            pass.put("error", CommonHelpers.getErrorMessage("db"));
        } catch (ValidationException e) {
            pass.put("error", "validation");
            pass.put("field", e.getField());
        }
        redirect(req, resp, pass);
    }
}
