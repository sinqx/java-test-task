package com.testTask.test.controller;

import com.testTask.test.entity.User;
import com.testTask.test.model.AuthModel;
import com.testTask.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthAndRegistrationContoller {
    @Autowired
    private UserService userService;


    @PostMapping("/sign-up")
    public ResponseEntity save(@RequestBody User user) {
        try {
            User newUser = userService.saveWithPasswordEncode(user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity getToken(@RequestBody AuthModel authModel) {
        try {
            String token = userService.getTokenByAuthModel(authModel);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }
}
