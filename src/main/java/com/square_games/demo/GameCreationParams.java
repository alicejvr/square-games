package com.square_games.demo;

import java.util.List;

public class GameCreationParams {
    private String gameType;
    private int numberOfPlayers;
    private int boardSize;
    private List<String> opponentIds;

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public List<String> getOpponentIds() {
        return opponentIds;
    }

    public void setOpponentIds(List<String> opponentIds) {
        this.opponentIds = opponentIds;
    }
}