package com.app.ecom.controller;


import com.app.ecom.dto.UserDTO;
import com.app.ecom.models.User;
import com.app.ecom.services.UserService;
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
    public ResponseEntity<List<UserDTO>> getAllUsers(){

        return ResponseEntity.ok(userService.fetchAllUsers());
    }

    @PostMapping("")
    public ResponseEntity<String> createUser(@RequestBody UserDTO user){
        userService.addUser(user);
        return ResponseEntity.ok("User Added Successfully");
    }
    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        return userService.fetchUserById(id).map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }
    @PutMapping ("{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id,@RequestBody UserDTO user){
        if(userService.updateUser(user,id) != null)
        return ResponseEntity.ok("User Updated Successfully");
        else
        return ResponseEntity.notFound().build();
    }


}
