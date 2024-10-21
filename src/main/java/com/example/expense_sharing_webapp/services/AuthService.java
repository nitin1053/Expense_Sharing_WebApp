//package com.example.expense_sharing_webapp.services;
//
//
//
//import com.example.expense_sharing_webapp.models.User;
//import com.example.expense_sharing_webapp.repositories.UserRepository;
//import com.example.expense_sharing_webapp.utils.JwtTokenUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private JwtTokenUtil jwtTokenUtil;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    public User registerUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
//        return userRepository.save(user);
//    }
//
//    public String login(String email, String password) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        if (passwordEncoder.matches(password, user.getPassword())) {
//            return jwtTokenUtil.generateToken(user.getEmail());
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }
//    }
//}
