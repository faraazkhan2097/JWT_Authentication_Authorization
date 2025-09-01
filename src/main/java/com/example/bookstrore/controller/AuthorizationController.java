package com.example.bookstrore.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookstore")
public class AuthorizationController {
    @GetMapping("/demo")
    public ResponseEntity<String> demo(){
        return ResponseEntity.ok("Hello from secured URL");
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly(){
        return ResponseEntity.ok("Hello from admin only URL");
    }
}
