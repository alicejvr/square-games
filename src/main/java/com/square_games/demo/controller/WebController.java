package com.square_games.demo.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.ParameterizedTypeReference;
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
    public String home() {
        return "login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response) {

        Map<String, String> request = Map.of(
                "username", username,
                "password", password
        );

        Map<String, String> loginResponse = restClient.post()
                .uri("http://localhost:8081/auth/login")
                .body(request)
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, String>>() {});

        String token = loginResponse.get("token");

        System.out.println("JWT reçu : " + token);

        // Création du cookie contenant le JWT
        Cookie cookie = new Cookie("JWT", token);

        // Le cookie sera envoyé uniquement avec les requêtes HTTP
        cookie.setHttpOnly(true);

        // Le cookie est valable pendant 1 heure
        cookie.setMaxAge(60 * 60);

        // Le cookie est valable sur toute l'application
        cookie.setPath("/");

        response.addCookie(cookie);

        System.out.println("Login OK");

        return "redirect:/game-home";
    }

    @GetMapping("/game-home")
    public String games() {
        return "game-home";
    }


}