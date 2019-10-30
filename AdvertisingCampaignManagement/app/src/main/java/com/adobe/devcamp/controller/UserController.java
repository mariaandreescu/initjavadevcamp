package com.adobe.devcamp.controller;

import com.adobe.devcamp.model.Gender;
import com.adobe.devcamp.model.User;
import com.adobe.devcamp.service.GenericService;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.valueOf;

@RestController
public class UserController {
    private final GenericService<User> userService;

    public UserController(GenericService<User> userService) {
        this.userService = userService;
    }

    //get all users
    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers () {
        return new ArrayList<>(userService.getAll(User.class).values());
    }

    //get all male users
    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE, params = "gender")
    public List<User> getUsersByGender(@RequestParam (name = "gender") String gender) {
        final List<User> users = new ArrayList<>(userService.getAll(User.class).values());

        return users.stream()
                .filter(user -> user.getProfile().getGender() == Gender.valueOf(gender))
                .collect(Collectors.toList());
    }

}
