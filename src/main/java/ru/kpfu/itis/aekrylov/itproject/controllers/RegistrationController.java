package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kpfu.itis.aekrylov.itproject.entities.User;
import ru.kpfu.itis.aekrylov.itproject.forms.RegistrationForm;
import ru.kpfu.itis.aekrylov.itproject.misc.CommonHelpers;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;
import ru.kpfu.itis.aekrylov.itproject.servlets.BaseServlet;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.IOException;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 12:40 PM
 */
@Controller("/register")
public class RegistrationController extends BaseServlet {
    
    private UserService userService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    protected String doPost(@ModelAttribute @Valid RegistrationForm form, BindingResult result,
                            ModelMap params) throws ServletException, IOException {
        if(result.hasErrors()) {
            FieldError e = result.getFieldError();
            params.put("error", "validation");
            params.put("cause", e.getDefaultMessage());
            params.put("field", e.getField());
            return "redirect:/register";
        }

        //todo move to validator
        if(!form.getPassword().equals(form.getPassword_repeat())) {
            params.put("error", "password_match");
            return "redirect:/register";
        }
        else if(userService.exists(form.getUsername())) {
            params.put("error", "username_taken");
            return "redirect:/register";
        }

        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword_raw(form.getPassword());
        user.setName(form.getName());
        user.setUsername(form.getUsername());

        userService.create(user);
        return "redirect:/";
    }

    @GetMapping
    protected String doGet(ModelMap dataModel) throws ServletException, IOException {
        String errorCode = (String) dataModel.get("error");
        if(errorCode != null) {
            dataModel.put("error", CommonHelpers.getErrorMessage(errorCode));
        }
        return "registration";
    }

}
