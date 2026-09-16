package com.square_games.demo.dao;

import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Optional;
import java.util.stream.Stream;

public class InMemoryGameDao implements GameDao{

    @Override
    public Stream<Game> findAll() {
        return Stream.empty();
    }

    @Override
    public Optional<Game> findById(String gameId) {
        return Optional.empty();
    }

    @Override
    public Game upsert(Game game) {
        return null;
    }

    @Override
    public void delete(String gameId) {

    }
}
