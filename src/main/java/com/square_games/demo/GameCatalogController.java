package com.square_games.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class GameCatalogController {

    private GameCatalog gameCatalog;

    public GameCatalogController(GameCatalog gameCatalog) {
        this.gameCatalog = gameCatalog;
    }

    @GetMapping("/gameCatalog")
    public Collection<String> getGameIds() {
        return gameCatalog.getGameIds();
    }
}
