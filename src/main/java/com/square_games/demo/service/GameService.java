package com.square_games.demo.service;

import com.square_games.demo.plugin.GamePlugin;
import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class GameService {

    private final Map<String, GamePlugin> gamePluginsByName;

    public GameService(List<GamePlugin> gamePlugins) {
        this.gamePluginsByName = new HashMap<>();
        for (GamePlugin plugin : gamePlugins) {
            gamePluginsByName.put(plugin.getId(), plugin);
        }
    }

    public Game createGame(String gameName) {
        GamePlugin plugin = gamePluginsByName.get(gameName);
        return plugin.createGame();
    }

    public String getGameName(String gameName, Locale locale) {
        GamePlugin plugin = gamePluginsByName.get(gameName);
        return plugin.getName(locale);
    }
}