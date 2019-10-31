package com.adobe.devcamp;

import com.adobe.devcamp.http.WebClient;
import com.adobe.devcamp.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class Application {
    private static WebClient webClient;

    public Application(WebClient webClient) {
        this.webClient = webClient;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
        final List<User> users = webClient.getUsers("/users");
        users.forEach(System.out::println);

        final List<User> boys = webClient.getUsersByGender("/users", "MALE");
        boys.forEach(System.out::println);



    }


}
