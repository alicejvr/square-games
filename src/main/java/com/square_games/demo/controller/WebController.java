package com.square_games.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Controller
public class WebController {

    private final RestClient restClient;

    public WebController() {
        this.restClient = RestClient.create();
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password) {

        Map<String, String> request = Map.of(
                "username", username,
                "password", password
        );

        String response = restClient.post()
                .uri("http://localhost:8081/auth/login")
                .body(request)
                .retrieve()
                .body(String.class);

        System.out.println("Réponse de api-user : " + response);

        return "login";
    }
}