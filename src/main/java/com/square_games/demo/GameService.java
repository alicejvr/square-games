package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameService {

    private final TicTacToeGameFactory ticTacToeGameFactory;
    private final ConnectFourGameFactory connectFourGameFactory;

    public GameService() {
        this.ticTacToeGameFactory = new TicTacToeGameFactory();
        this.connectFourGameFactory = new ConnectFourGameFactory();
    }

    public Game createGame(GameCreationParams params) {
        if (true) {
            return ticTacToeGameFactory.createGame(
                    params.getNumberOfPlayers(),
                    params.getBoardSize()
            );
        } else {
            return connectFourGameFactory.createGame(
                    params.getNumberOfPlayers(),
                    params.getBoardSize()
            );
        }
    }


    public Game getGame(UUID gameId) {
        return null;
    }
}