package com.app.ecom;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public User fetchUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }
}
