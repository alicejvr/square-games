package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TicTacToePlugin implements GamePlugin {

    private final TicTacToeGameFactory gameFactory = new TicTacToeGameFactory();
    private final MessageSource messageSource;

    @Value("${game.tictactoe.default-player-count}")
    private int playerCount;

    @Value("${game.tictactoe.default-board-size}")
    private int boardSize;

    public TicTacToePlugin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getId() {
        return "tictactoe";
    }

    @Override
    public Game createGame() {
        return gameFactory.createGame(playerCount, boardSize);
    }

    @Override
    public String getName(Locale locale) {
        return messageSource.getMessage("game.tictactoe.name", null, locale);
    }

}