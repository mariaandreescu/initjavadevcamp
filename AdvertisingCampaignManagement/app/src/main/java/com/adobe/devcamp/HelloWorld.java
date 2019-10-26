package com.adobe.devcamp;


import com.adobe.devcamp.model.User;
import com.adobe.devcamp.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class HelloWorld {
    private static UserService userService;

    public HelloWorld(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloWorld.class);
        final Map<Integer, User> users = userService.getUsers();
        users.entrySet().stream().forEach(System.out::println);
    }

}
