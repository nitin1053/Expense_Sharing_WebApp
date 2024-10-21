//package com.example.expense_sharing_webapp.controllers;
//
//
//
//
//
//
//import com.example.expense_sharing_webapp.models.AuthRequest;
//import com.example.expense_sharing_webapp.models.User;
//import com.example.expense_sharing_webapp.services.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private AuthService authService;
//
//    @PostMapping("/register")
//    public ResponseEntity<User> registerUser(@RequestBody User user) {
//        return ResponseEntity.ok(authService.registerUser(user));
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> loginUser(@RequestBody AuthRequest authRequest) {
//        String token = authService.login(authRequest.getEmail(), authRequest.getPassword());
//        return ResponseEntity.ok(token);
//    }
//}
