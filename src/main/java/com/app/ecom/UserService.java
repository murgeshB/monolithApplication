package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private List<User> users= new ArrayList<>();

    public List<User> fetchAllUsers() {
        return users;
    }
    public List<User> addUser(User user) {
        users.add(user);
        return users;
    }

    public Optional<User> fetchUserById(int id) {
        return users.stream().filter(user -> user.getId() == id).findFirst();
    }
}
