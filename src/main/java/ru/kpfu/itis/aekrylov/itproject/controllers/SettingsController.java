package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.misc.ValidationException;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

import java.sql.SQLException;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/25/17 9:29 PM
 */

@Controller
@RequestMapping("/user/settings")
public class SettingsController {

    private UserService userService;
    private PasswordEncoder encoder;

    @Autowired
    public SettingsController(UserService userService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping
    protected String doGet(ModelMap dataModel) {
        User user = CommonHelpers.getCurrentUser();
        dataModel.put("user", user);
        return "settings";
    }

    @PostMapping
    protected String doPost(@RequestParam Map<String, String> params, ModelMap modelMap) {
        String act =  params.get("act");

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

                    if(!encoder.matches(old, user.getPassword())) {
                        modelMap.put("error", CommonHelpers.getErrorMessage("password_invalid"));
                    } else if(new1 == null || !new1.equals(new2)) {
                        modelMap.put("error", CommonHelpers.getErrorMessage("password_match"));
                    } else {
                        userService.updatePassword(user, new1);
                    }
                    break;
            }
        } catch (ValidationException e) {
            modelMap.put("error", "validation");
            modelMap.put("field", e.getField());
        }
        return "redirect:/user/settings";
    }
}
