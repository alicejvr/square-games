package com.square_games.demo.service;

import com.square_games.demo.plugin.GamePlugin;
import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class GameService {


    private final GamePlugin gamePlugin;

    public GameService(GamePlugin gamePlugin) {
        this.gamePlugin = gamePlugin;
    }

    public Game createGame() {
        return gamePlugin.createGame();
    }

    public String getGameName(Locale locale) {
        return gamePlugin.getName(locale);
    }

}