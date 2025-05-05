package com.CardManagmentSystem.service;

import com.CardManagmentSystem.model.User;
import com.CardManagmentSystem.repository.UserRepository;
import com.CardManagmentSystem.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTUtil jwtUtil;

    public User register(User user) {
        // Логика для регистрации нового пользователя
        return userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return jwtUtil.generateToken(user.getEmail());
        }
        throw new RuntimeException("Invalid credentials");
    }
}
