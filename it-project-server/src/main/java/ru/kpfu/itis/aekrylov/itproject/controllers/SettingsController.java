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
import ru.kpfu.itis.aekrylov.itproject.misc.WebHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

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
        User user = WebHelpers.getCurrentUser();
        dataModel.put("user", user);
        return "settings";
    }

    @PostMapping
    protected String doPost(@RequestParam Map<String, String> params, ModelMap modelMap) {
        String act =  params.get("act");

        User user = WebHelpers.getCurrentUser();
        //user is never null due to web app structure
        //todo validation
        assert user != null;
        switch (act) {
            case "info":
                if(params.get("username") != null) user.setUsername(params.get("username"));
                if(params.get("name") != null) user.setName(params.get("name"));
                if(params.get("email") != null) user.setEmail(params.get("email"));

                userService.save(user);
                break;

            case "password":
                String old = params.get("password_old");
                String new1 = params.get("password_new1");
                String new2 = params.get("password_new2");

                if(!encoder.matches(old, user.getPassword())) {
                    modelMap.put("error", WebHelpers.getErrorMessage("password_invalid"));
                } else if(new1 == null || !new1.equals(new2)) {
                    modelMap.put("error", WebHelpers.getErrorMessage("password_match"));
                } else {
                    userService.updatePassword(user, new1);
                }
                break;
        }
        return "redirect:/user/settings";
    }
}
