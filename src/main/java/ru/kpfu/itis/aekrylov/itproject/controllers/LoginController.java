package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 5/12/17 1:52 PM
 */

@Controller("/login")
public class LoginController {

    @GetMapping
    public String doGet() {
        return "login";
    }
}
