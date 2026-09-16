package com.square_games.demo.service;

import com.square_games.demo.dao.GameDao;
import com.square_games.demo.plugin.GamePlugin;
import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.stereotype.Service;
import static java.util.Locale.FRENCH;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class GameService {

    private final Map<String, GamePlugin> gamePluginsByName;
    private final GameDao gameDao;

    public GameService(List<GamePlugin> gamePlugins, GameDao gameDao) {
        this.gameDao = gameDao;
        this.gamePluginsByName = new HashMap<>();
        for (GamePlugin plugin : gamePlugins) {
            gamePluginsByName.put(plugin.getName(FRENCH), plugin);
        }
    }

    public Game createGame(String gameName) {
        GamePlugin plugin = gamePluginsByName.get(gameName);
        Game game = plugin.createGame();

        return gameDao.upsert(game);
    }

    public String getGameName(String gameName, Locale locale) {
        GamePlugin plugin = gamePluginsByName.get(gameName);
        return plugin.getName(locale);
    }
}