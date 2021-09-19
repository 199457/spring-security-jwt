package org.wei.securityjwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wei.securityjwt.service.UserService;
import org.wei.securityjwt.vo.AuthRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody AuthRequest request) {


        return null;
    }
}
