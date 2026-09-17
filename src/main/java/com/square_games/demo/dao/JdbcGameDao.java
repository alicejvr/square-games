package com.square_games.demo.dao;

import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Primary
@Repository
public class JdbcGameDao implements GameDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcGameDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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