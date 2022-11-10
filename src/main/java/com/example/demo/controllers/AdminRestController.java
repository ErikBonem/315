package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminRestController {

    private UserService userService;

    @Autowired
    public AdminRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> index(){
    List<User> allUsers = userService.findAll();
    return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }


    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<?> newUser(@RequestBody User user) {
    userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/users")
    public ResponseEntity<?> editUser (@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("authentication")
    public ResponseEntity<?> showOneUsers() {
        return new ResponseEntity<>(userService.getAuthUser(), HttpStatus.OK);}
}

