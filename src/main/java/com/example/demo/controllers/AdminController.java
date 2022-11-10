package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService us;

    @Autowired
    public AdminController(UserService us, RoleRepository rr) {
        this.us = us;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", us.findAll());
        model.addAttribute("user", us.getAuthUser());
        model.addAttribute("allRoles", us.getAllRoles());
        model.addAttribute("newUser", new User());
        return "admin";
    }


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newUser(@ModelAttribute User newuser, @RequestParam("rolesSelected") Long[] roles) {
        us.save(newuser, roles);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        us.deleteById(id);
        return "redirect:/admin";
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "/edit/{id}")
    public String editUser(@ModelAttribute("userEd") User user, @PathVariable("id") Long id, Model model, @RequestParam("rolesSelected") Long[] roles) {
        model.addAttribute("userEd", us.getById(id));
        us.update(user, roles);
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
        return "redirect:/login";
    }
}
