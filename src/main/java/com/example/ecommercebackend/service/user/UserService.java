package com.example.ecommercebackend.service.user;

import com.example.ecommercebackend.entity.user.Guest;
import com.example.ecommercebackend.entity.user.User;
import com.example.ecommercebackend.exception.ExceptionMessage;
import com.example.ecommercebackend.exception.NotFoundException;
import com.example.ecommercebackend.repository.user.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User "+ ExceptionMessage.NOT_FOUND.getMessage()));
    }
    public User getUserByUsernameOrNull(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public boolean isUserExistByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isUserExistByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
