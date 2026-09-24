package com.square_games.demo.dao;

import com.square_games.demo.entities.GameEntity;
import com.square_games.demo.entities.GameEntityRepository;
import com.square_games.demo.entities.GameTokenEntity;
import fr.le_campus_numerique.square_games.engine.*;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGameFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Stream;

@Primary
@Repository
public class JpaGameDao implements GameDao {

    private final GameEntityRepository gameEntityRepository;

    private final Map<String, GameFactory> factories = new HashMap<>();

    public JpaGameDao(GameEntityRepository gameEntityRepository) {
        this.gameEntityRepository = gameEntityRepository;

        GameFactory ticTacToe = new TicTacToeGameFactory();
        GameFactory connectFour = new ConnectFourGameFactory();
        GameFactory taquin = new TaquinGameFactory();

        factories.put(ticTacToe.getGameFactoryId(), ticTacToe);
        factories.put(connectFour.getGameFactoryId(), connectFour);
        factories.put(taquin.getGameFactoryId(), taquin);
    }

    @Override
    public Stream<Game> findAll() {
        return gameEntityRepository.findAll()
                .stream()
                .map(this::toGame);
    }

    @Override
    public Stream<Game> findByPlayerId(UUID playerId) {
        return findAll()
                .filter(game -> game.getPlayerIds().contains(playerId));
    }

    @Override
    public Optional<Game> findById(String gameId) {
        return gameEntityRepository.findById(gameId)
                .map(this::toGame);
    }

    @Override
    public Game upsert(Game game) {
        gameEntityRepository.save(toEntity(game));
        return game;
    }

    @Override
    public void delete(String gameId) {
        gameEntityRepository.deleteById(gameId);
    }

    // Conversion Game → GameEntity
    private GameEntity toEntity(Game game) {

        GameEntity entity = new GameEntity();

        entity.id = game.getId().toString();
        entity.factoryId = game.getFactoryId();
        entity.boardSize = game.getBoardSize();

        entity.playerIds = game.getPlayerIds();

        System.out.println("Jeu sauvegardé : " + game.getFactoryId());
        System.out.println("Nombre de tokens restants : " + game.getRemainingTokens().size());
        System.out.println("Nombre de tokens retirés : " + game.getRemovedTokens().size());

        // Sauvegarde des tokens présents sur le plateau
        entity.tokens = game.getBoard().entrySet().stream()
                .map(entry -> {
                    CellPosition position = entry.getKey();
                    Token token = entry.getValue();

                    GameTokenEntity tokenEntity = new GameTokenEntity();

                    tokenEntity.ownerId = token.getOwnerId()
                            .map(UUID::toString)
                            .orElse(null);

                    tokenEntity.name = token.getName();
                    tokenEntity.removed = false;
                    tokenEntity.x = position.x();
                    tokenEntity.y = position.y();

                    return tokenEntity;
                })
                .collect(java.util.stream.Collectors.toList());

// Ajout des éventuels tokens retirés
        entity.tokens.addAll(
                game.getRemovedTokens().stream()
                        .map(token -> {
                            GameTokenEntity tokenEntity = new GameTokenEntity();

                            tokenEntity.ownerId = token.getOwnerId()
                                    .map(UUID::toString)
                                    .orElse(null);

                            tokenEntity.name = token.getName();
                            tokenEntity.removed = true;

                            return tokenEntity;
                        })
                        .toList()
        );

        return entity;
    }

    // Conversion GameEntity → Game
    private Game toGame(GameEntity entity) {

        GameFactory factory = factories.get(entity.factoryId);

        Set<UUID> players = entity.playerIds;

        // Récupération des tokens présents sur le plateau
        Collection<TokenPosition<UUID>> boardTokens = entity.tokens.stream()
                .filter(token -> !token.removed)
                .map(token -> new TokenPosition<>(
                        token.ownerId == null ? null : UUID.fromString(token.ownerId),
                        token.name,
                        token.x,
                        token.y
                ))
                .toList();

        // Récupération des tokens retirés
        Collection<TokenPosition<UUID>> removedTokens = entity.tokens.stream()
                .filter(token -> token.removed)
                .map(token -> new TokenPosition<>(
                        token.ownerId == null ? null : UUID.fromString(token.ownerId),
                        token.name,
                        0,
                        0
                ))
                .toList();

        System.out.println("Joueurs récupérés : " + players);
        System.out.println("Nombre de joueurs récupérés : " + players.size());

        System.out.println("Nombre de tokens en base : " + entity.tokens.size());
        System.out.println("Nombre de boardTokens : " + boardTokens.size());
        System.out.println("Nombre de removedTokens : " + removedTokens.size());

        try {
            return factory.createGameWithIds(
                    UUID.fromString(entity.id),
                    entity.boardSize,
                    new ArrayList<>(players),
                    boardTokens,
                    removedTokens
            );
        } catch (InconsistentGameDefinitionException e) {
            System.out.println("ptit pb");
            throw new RuntimeException(e);
        }
    }
}