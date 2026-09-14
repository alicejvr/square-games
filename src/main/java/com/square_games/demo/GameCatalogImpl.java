package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class GameCatalogImpl implements GameCatalog{

    private TicTacToeGameFactory ticTacToeGameFactory;
    private final ConnectFourGameFactory connectFourGameFactory;

    public GameCatalogImpl() {
        this.ticTacToeGameFactory = new TicTacToeGameFactory();
        this.connectFourGameFactory = new ConnectFourGameFactory();
    }

    public Collection<String> getGameIds() {
        return List.of(
                ticTacToeGameFactory.getGameFactoryId(),
                connectFourGameFactory.getGameFactoryId()
        );
    }

}
