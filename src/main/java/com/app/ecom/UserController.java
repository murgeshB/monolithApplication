package com.app.ecom;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User Added Successfully");
    }
    @GetMapping("api/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable int id){
        return userService.fetchUserById(id).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
    @PutMapping ("/api/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable int id,@RequestBody User user){
        if(userService.updateUser(user,id) != null)
        return ResponseEntity.ok("User Updated Successfully");
        else
        return ResponseEntity.notFound().build();
    }


}
