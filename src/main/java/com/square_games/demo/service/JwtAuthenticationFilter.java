package com.square_games.demo.service;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        System.out.println("JWT FILTER : requête reçue");

// Récupère l'en-tête Authorization
        String authHeader = request.getHeader("Authorization");

        String token = null;

// Si le JWT est envoyé dans Authorization: Bearer
        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            System.out.println("JWT FILTER : Bearer trouvé");

            token = authHeader.substring(7);
        }

// Sinon, cherche le JWT dans le cookie
        if (token == null && request.getCookies() != null) {

            for (Cookie cookie : request.getCookies()) {

                if ("JWT".equals(cookie.getName())) {

                    System.out.println("JWT FILTER : cookie JWT trouvé");

                    token = cookie.getValue();
                    break;
                }
            }
        }

// Si on a trouvé un JWT
        if (token != null) {

            // Vérifie que le JWT est valide
            if (jwtService.isTokenValid(token)) {

                System.out.println("JWT FILTER : token valide");

                // Récupère l'utilisateur dans le JWT
                String username = jwtService.extractUsername(token);

                System.out.println("JWT FILTER : username = " + username);

                // Récupère les rôles dans le JWT
                List<String> roles = jwtService.extractRoles(token);

                // Transforme les rôles pour que Spring Security les comprenne
                List<SimpleGrantedAuthority> authorities = roles.stream()
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                // Crée l'authentification
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                authorities
                        );

                // Enregistre l'utilisateur comme authentifié
                SecurityContextHolder.getContext()
                        .setAuthentication(authentication);
            }
        }

        // Laisse la requête continuer
        filterChain.doFilter(request, response);
    }
}