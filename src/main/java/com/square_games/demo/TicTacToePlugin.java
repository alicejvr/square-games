package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TicTacToePlugin implements GamePlugin {

    private final TicTacToeGameFactory gameFactory = new TicTacToeGameFactory();

    @Value("${game.tictactoe.default-player-count}")
    private int playerCount;

    @Value("${game.tictactoe.default-board-size}")
    private int boardSize;

    @Override
    public Game createGame() {
        return gameFactory.createGame(playerCount, boardSize);
    }

    @Override
    public String getName(Locale locale) {
        return "Tic-Tac-Toe";
    }
}