package com.square_games.demo.dao;

import com.square_games.demo.entities.GameEntity;
import com.square_games.demo.entities.GameEntityRepository;
import com.square_games.demo.entities.GameTokenEntity;
import fr.le_campus_numerique.square_games.engine.Game;
import fr.le_campus_numerique.square_games.engine.GameFactory;
import fr.le_campus_numerique.square_games.engine.InconsistentGameDefinitionException;
import fr.le_campus_numerique.square_games.engine.TokenPosition;
import fr.le_campus_numerique.square_games.engine.connectfour.ConnectFourGameFactory;
import fr.le_campus_numerique.square_games.engine.taquin.TaquinGameFactory;
import fr.le_campus_numerique.square_games.engine.tictactoe.TicTacToeGame;
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

        entity.playerIds = String.join(
                ",",
                game.getPlayerIds().stream()
                        .map(UUID::toString)
                        .toList()
        );

        /*entity.tokens = game.getRemainingTokens().stream()
                .map(token -> {
                    GameTokenEntity tokenEntity = new GameTokenEntity();

                    tokenEntity.ownerId = token.getOwnerId()
                            .map(UUID::toString)
                            .orElse(null);

                    tokenEntity.name = token.getName();
                    tokenEntity.removed = false;
                    tokenEntity.x = token.getPosition().x();
                    tokenEntity.y = token.getPosition().y();

                    return tokenEntity;
                })
                .collect(java.util.stream.Collectors.toList());

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
        );*/

        return entity;
    }

    // Conversion GameEntity → Game
    private Game toGame(GameEntity entity){

        GameFactory factory = factories.get(entity.factoryId);

        List<UUID> players = List.of(entity.playerIds.split(","))
                .stream()
                .map(UUID::fromString)
                .toList();

        /*Collection<TokenPosition<UUID>> boardTokens = entity.tokens.stream()
                .filter(token -> !token.removed)
                .map(token -> new TokenPosition<>(
                        token.ownerId == null ? null : UUID.fromString(token.ownerId),
                        token.name,
                        token.x,
                        token.y
                ))
                .toList();

        Collection<TokenPosition<UUID>> removedTokens = entity.tokens.stream()
                .filter(token -> token.removed)
                .map(token -> new TokenPosition<>(
                        token.ownerId == null ? null : UUID.fromString(token.ownerId),
                        token.name,
                        0,
                        0
                ))
                .toList();*/

        try {
            return factory.createGameWithIds(
                    UUID.fromString(entity.id),
                    entity.boardSize,
                    players,
                    Collections.emptyList(),
                    Collections.emptyList()
            );
        } catch (InconsistentGameDefinitionException e) {
            System.out.println("pti pb");
            throw new RuntimeException(e);
        }
    }
}