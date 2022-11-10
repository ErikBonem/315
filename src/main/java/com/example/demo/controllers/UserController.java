package com.example.demo.controllers;

import com.example.demo.FillTables;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    private final UserService userService;
    private final FillTables fillTables;
    @Autowired
    public UserController(UserService userService, FillTables fillTables) {
        this.userService = userService;
        this.fillTables = fillTables;
    }

    @RequestMapping("/user")
    public String show(Model model) {
        model.addAttribute("user", userService.getAuthUser());
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
