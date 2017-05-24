package ru.kpfu.itis.aekrylov.itproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * By Anton Krylov (anthony.kryloff@gmail.com)
 * Date: 10/19/16 6:35 PM
 * 11-501
 * Home page servlet
 */

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String mainGet() {
        return "main";
    }
}
