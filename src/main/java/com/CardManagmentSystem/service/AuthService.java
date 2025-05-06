package com.CardManagmentSystem.service;

import com.CardManagmentSystem.model.User;
import com.CardManagmentSystem.repository.UserRepository;
import com.CardManagmentSystem.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User register(User user) {
        // Шифрование пароля перед сохранением
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials")); // Извлечение User из Optional
        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(user.getEmail());
        }
        throw new RuntimeException("Invalid credentials");
    }
}
