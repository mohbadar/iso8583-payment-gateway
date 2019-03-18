/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.console.controller;

import com.console.config.aspect.Loggable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Dell
 */
@Controller
public class HomeController {

    @Loggable
    @GetMapping("/")
    public String root() {
        return "index";
    }

    @Loggable
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @Loggable
    @GetMapping("/access-denied")
    public String accessDenied() {
        return "/error/403";
    }

}
