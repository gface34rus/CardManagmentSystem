package com.CardManagmentSystem.service;

import com.CardManagmentSystem.model.Role;
import com.CardManagmentSystem.model.User;
import com.CardManagmentSystem.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        userService = new UserService(userRepository, passwordEncoder);
    }

    @Test
    public void testRegisterUser_Success() {
        User user = new User("test@example.com", "password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals("test@example.com", registeredUser.getEmail());
        assertEquals(Role.USER, registeredUser.getRole());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindByEmail_Success() {
        User user = new User("test@example.com", "password");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail("test@example.com");

        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail("test@example.com");
    }

    @Test
    public void testRegisterUser_ExistingEmail() {
        User user = new User("test@example.com", "password");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    public void testFindByEmail_NotFound() {
        when(userRepository.findByEmail("notfound@example.com")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.findByEmail("notfound@example.com");
        });

        assertEquals("User not found", exception.getMessage());
    }

    @Test
    public void testRegisterUser_EmptyEmail() {
        User user = new User("", "password");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("Email cannot be empty", exception.getMessage());
    }

    @Test
    public void testRegisterUser_EmptyPassword() {
        User user = new User("test@example.com", "");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(user);
        });

        assertEquals("Password cannot be empty", exception.getMessage());
    }
}