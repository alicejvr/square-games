package com.square_games.demo.dao;

import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@Repository // composant chargé de l'accès aux données
public class InMemoryGameDao implements GameDao{

    private final Map<String, Game> games = new HashMap<>(); // crée un stockage temporaire

    @Override
    public Stream<Game> findAll() {
        return games.values().stream(); // on prend ttes les parties présentes dans la map
    }

    @Override
    public Optional<Game> findById(String gameId) {
        return Optional.ofNullable(games.get(gameId)); // on demande à la map de ns donner le game associé à un Id
    }

    @Override
    public Game upsert(Game game) {
        String gameId = game.getId().toString(); // on récupère l'ID du game
        games.put(gameId, game); // on le met ds la map
        return game;
    }

    @Override
    public void delete(String gameId) {
        games.remove(gameId); // on supp la partie de la map
    }
}
