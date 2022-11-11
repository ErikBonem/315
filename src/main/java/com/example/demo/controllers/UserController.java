package com.example.demo.controllers;

import com.example.demo.FillTables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    private final FillTables fillTables;
    @Autowired
    public UserController(FillTables fillTables) {
        this.fillTables = fillTables;
    }

    @RequestMapping("/user")
    public String show() {
        return "user";    }

    @GetMapping("/login")
    public String home() {
        return "login";
    }

    @GetMapping("")
    public String homepage() {
        fillTables.fillTables();
        return "login";
    }

    @GetMapping("user/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/login";
    }
}
