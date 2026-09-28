package com.square_games.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import java.util.List;
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
                .body(new ParameterizedTypeReference<Map<String, String>>() {
                });

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

        System.out.println("--------------- Login OK");

        // redirige vers la page games-home.html
        return "redirect:/games-home";
    }

    @GetMapping("/games-home")
    public String games(Authentication authentication, Model model, HttpServletRequest request) {

        // Récupère le nom de l'utilisateur connecté
        String username = authentication.getName();

        // Envoie le nom à Thymeleaf
        model.addAttribute("username", username);

        // Cherche le JWT dans les cookies
        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Appelle l'API des parties
        String[] games = restClient.get()
                .uri("http://localhost:8080/gamesForUser")
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(String[].class);

        // Envoie les parties à Thymeleaf
        model.addAttribute("games", List.of(games));
        return "games-home";
    }

    @GetMapping("/game/{id}")
    public String game(
            @PathVariable String id,
            Model model,
            HttpServletRequest request) {

        // Récupère le JWT dans le cookie
        String token = null;

        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("JWT".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // Récupère les informations de la partie
        Map<String, Object> game = restClient.get()
                .uri("http://localhost:8080/games/" + id)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .body(Map.class);

        // Envoie la partie à Thymeleaf
        model.addAttribute("game", game);

        return "game";
    }

}