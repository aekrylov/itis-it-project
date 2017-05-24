package ru.kpfu.itis.aekrylov.itproject.controllers.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.aekrylov.itproject.services.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/24/17 12:48 PM
 */

@RestController
@RequestMapping("/ajax/check_username")
public class RegistrationAjaxController {

    private UserService userService;

    @Autowired
    public RegistrationAjaxController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    protected Object doGet(@RequestParam("username") String username) {
        Map<String, Object> map = new HashMap<>();
        map.put("available", !userService.exists(username));
        return map;
    }

}
