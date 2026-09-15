package com.square_games.demo.service;

import com.square_games.demo.plugin.ConnectFourPlugin;
import com.square_games.demo.plugin.TaquinPlugin;
import com.square_games.demo.plugin.TicTacToePlugin;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameCatalogImpl implements GameCatalog {

    private final TicTacToePlugin ticTacToePlugin;
    private final ConnectFourPlugin connectFourPlugin;
    private final TaquinPlugin taquinPlugin;


    public GameCatalogImpl(
            TicTacToePlugin ticTacToePlugin,
            ConnectFourPlugin connectFourPlugin,
            TaquinPlugin taquinPlugin) {

        this.ticTacToePlugin = ticTacToePlugin;
        this.connectFourPlugin = connectFourPlugin;
        this.taquinPlugin = taquinPlugin;
    }

    @Override
    public Map<String, String> getGames(Locale locale) {

        Map<String, String> games = new LinkedHashMap<>();

        games.put(
                ticTacToePlugin.getId(),
                ticTacToePlugin.getName(locale)
        );

        games.put(
                connectFourPlugin.getId(),
                connectFourPlugin.getName(locale)
        );

        games.put(
                taquinPlugin.getId(),
                taquinPlugin.getName(locale)
        );

        return games;
    }
}