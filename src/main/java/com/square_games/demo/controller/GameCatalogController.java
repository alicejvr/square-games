package com.square_games.demo.controller;

import com.square_games.demo.service.GameCatalog;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.Map;

@RestController
public class GameCatalogController {

    private GameCatalog gameCatalog;

    public GameCatalogController(GameCatalog gameCatalog) {
        this.gameCatalog = gameCatalog;
    }

    @Operation(summary = "Récupérer les jeux disponibles")
    @GetMapping("/gameCatalog")
    public Map<String, String> getGames(Locale locale) {
        return gameCatalog.getGames(locale);
    }
}
