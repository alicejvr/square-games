package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    private final TicTacToeGameFactory ticTacToeGameFactory;
    private final ConnectFourGameFactory connectFourGameFactory;

    public GameServiceImpl() {
        this.ticTacToeGameFactory = new TicTacToeGameFactory();
        this.connectFourGameFactory = new ConnectFourGameFactory();
    }

    @Override
    public Game createGame(GameCreationParams params) {
        return ticTacToeGameFactory.createGame(
                params.getNumberOfPlayers(),
                params.getBoardSize()
        );
    }

    @Override
    public Game getGame(UUID gameId) {
        return null;
    }
}