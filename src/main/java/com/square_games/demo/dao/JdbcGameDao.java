package com.square_games.demo.dao;

import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Repository
public class JdbcGameDao implements GameDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcGameDao(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        System.out.println("JDBC template ok");

        /*
        SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("id", 1);
        String result = jdbcTemplate.queryForObject("SELECT NAME FROM EMPLOYEE WHERE ID = :id", namedParameters, String.class);
        System.out.println(result);*/
    }

    @Override
    public Stream<Game> findAll() {
        return Stream.empty();
    }

    @Override
    public Stream<Game> findByPlayerId(UUID playerId) {
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