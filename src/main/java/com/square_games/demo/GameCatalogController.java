package com.square_games.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

@RestController
public class GameCatalogController {

    private GameCatalog gameCatalog;

    public GameCatalogController(GameCatalog gameCatalog) {
        this.gameCatalog = gameCatalog;
    }

    @GetMapping("/gameCatalog")
    public Map<String, String> getGames(Locale locale) {
        return gameCatalog.getGames(locale);
    }
}
