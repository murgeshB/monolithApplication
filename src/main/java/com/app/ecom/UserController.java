package com.app.ecom;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping("/api/users")
    public ResponseEntity<List<User>> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.addUser(user));
    }
    @GetMapping("api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id){
        if(userService.fetchUserById(id) == null)return ResponseEntity.notFound().build();
        return ResponseEntity.ok(userService.fetchUserById(id));
    }

}
