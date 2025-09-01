package com.example.bookstrore.controller;

import com.example.bookstrore.model.AuthenticationResponse;
import com.example.bookstrore.model.User;
import com.example.bookstrore.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookstore")
public class AuthenticationController {
    private AuthenticationService _authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this._authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User user){
        return ResponseEntity.ok(_authenticationService.register(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User user){
        return ResponseEntity.ok(_authenticationService.authenticate(user));
    }
}
