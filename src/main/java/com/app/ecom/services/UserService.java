package com.app.ecom.services;

import com.app.ecom.models.User;
import com.app.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }
    public void addUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> fetchUserById(Long id) {

        return userRepository.findById(id);
    }

    public User updateUser(User user, Long id) {

        return userRepository.findById(id).map(u -> { u.setFirstName(user.getFirstName());u.setLastName(user.getLastName()); userRepository.save(u);return u;}).orElseGet(null);
    }
}
