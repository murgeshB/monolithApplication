package com.app.ecom;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.ok("User Added Successfully");
    }
    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.fetchUserById(id).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
    @PutMapping ("{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody User user){
        if(userService.updateUser(user,id) != null)
        return ResponseEntity.ok("User Updated Successfully");
        else
        return ResponseEntity.notFound().build();
    }


}
