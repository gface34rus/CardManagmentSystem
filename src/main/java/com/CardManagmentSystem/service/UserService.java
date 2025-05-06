package com.CardManagmentSystem.service;

import com.CardManagmentSystem.model.Role;
import com.CardManagmentSystem.model.User;
import com.CardManagmentSystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER); // Устанавливаем роль по умолчанию
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(null);
    }
}
