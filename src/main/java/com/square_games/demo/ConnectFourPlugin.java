package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ConnectFourPlugin implements GamePlugin {

    private final ConnectFourGameFactory gameFactory = new ConnectFourGameFactory();

    @Value("${game.connectfour.default-player-count}")
    private int playerCount;

    @Value("${game.connectfour.default-board-size}")
    private int boardSize;

    @Override
    public Game createGame() {
        return gameFactory.createGame(playerCount, boardSize);
    }

    @Override
    public String getName(Locale locale) {
        return "Connect Four";
    }
}