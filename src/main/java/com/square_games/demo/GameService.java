package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.UUID;

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