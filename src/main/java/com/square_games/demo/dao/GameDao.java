package com.square_games.demo.dao;

import fr.le_campus_numerique.square_games.engine.Game;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface GameDao {
    // flux de plusieurs objets Game (stream permet de parcourir et récupérer des données)
    Stream<Game> findAll();

    Stream<Game> findByPlayerId(UUID playerId);

    // chercher la partie qui possède cet ID - la partie peut ne pas exister, d'où Optional
    Optional<Game> findById(String gameId);

    /* upsert = update + insert
    "Enregistre cette partie. Si elle existe déjà, mets-la à jour. Si elle n'existe pas, crée-la"
    1/ elle n'existe pas : INSERT (création)
    2/ la partie existe déjà : UPDATE (modification)
    Elle reçoit Game game puisqu'on lui donne directement la partie à enregistrer
    et retourne Game => renvoie la partie enregistrée.
     */
    Game upsert(Game game);

    // "Supprime la partie qui possède cet identifiant", ex: delete("53");
    void delete(String gameId);
}