package com.square_games.demo.plugin;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Component
public class ConnectFourPlugin implements GamePlugin {

    private final ConnectFourGameFactory gameFactory = new ConnectFourGameFactory();
    private final MessageSource messageSource;

    @Value("${game.connectfour.default-player-count}")
    private int playerCount;

    @Value("${game.connectfour.default-board-size}")
    private int boardSize;

    public ConnectFourPlugin(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public String getId() {
        return "connect4";
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
        return messageSource.getMessage("game.connectfour.name", null, locale);
    }


}