package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TaquinPlugin implements GamePlugin {

    private final TaquinGameFactory gameFactory = new TaquinGameFactory();

    @Value("${game.taquin.default-player-count}")
    private int playerCount;

    @Value("${game.taquin.default-board-size}")
    private int boardSize;

    @Override
    public Game createGame() {
        return gameFactory.createGame(playerCount, boardSize);
    }

    @Override
    public String getName(Locale locale) {
        return "Taquin";
    }
}