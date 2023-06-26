package com.example.testtask.demo.controllers;

import com.example.testtask.demo.dto.UserDto;
import com.example.testtask.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping("{id}")
    public UserDto getUserById(@PathVariable String id) {
        return service.getById(id);
    }

    @GetMapping("")
    public List<UserDto> findAllUsers() {
        return service.findAll();
    }

    @PostMapping("")
    public UserDto createUser(@RequestBody UserDto user) {
        return service.create(user);
    }

    @PutMapping("{id}")
    public UserDto updateUser(@PathVariable String id, @RequestBody UserDto user) {
        return service.update(id, user);
    }

    @DeleteMapping("{id}")
    public void deleteUserById(@PathVariable String id) {
        service.delete(id);
    }

}
