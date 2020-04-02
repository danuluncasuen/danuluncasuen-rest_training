package com.internship.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    @GetMapping("/")
    public ModelAndView index() {
        return new ModelAndView("index.html");
    }

    @GetMapping("/add")
    public ModelAndView addUser() {
        return new ModelAndView("addUser.html");
    }

    @GetMapping("/update")
    public ModelAndView updateUser() {
        return new ModelAndView("updateUser.html");
    }
}
