package com.square_games.demo.plugin;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Component
public class TaquinPlugin implements GamePlugin {

    private final TaquinGameFactory gameFactory = new TaquinGameFactory();
    private final MessageSource messageSource;

    @Value("${game.taquin.default-player-count}")
    private int playerCount;

    @Value("${game.taquin.default-board-size}")
    private int boardSize;

    public TaquinPlugin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getId() {
        return "15 puzzle";
    }

    @Override
    public Game createGame() {
        return gameFactory.createGame(playerCount, boardSize);
    }

    @Override
    public Game createGame(Set<UUID> playerIds) {
        return gameFactory.createGame(boardSize, playerIds);
    }

    @Override
    public String getName(Locale locale) {
        return messageSource.getMessage("game.taquin.name", null, locale);
    }

}