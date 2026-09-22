package com.square_games.demo.controller;

import com.square_games.demo.GameCreationParams;
import com.square_games.demo.dao.GameDao;
import com.square_games.demo.service.GameService;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class GameController {
    private final GameService gameService;
    private final GameDao gameDao;

    @Value("${user-service.url}")
    private String userServiceUrl;

    public GameController(GameService gameService, GameDao gameDao) {
        this.gameService = gameService;
        this.gameDao = gameDao;
        System.out.println("GameController :: constructeur : implémentation de GameDao : "+ this.gameDao.getClass());
    }

    public boolean checkUserExists(String id) {
        RestClient restClient = RestClient.create();

        Boolean result = restClient.get()
                .uri(userServiceUrl + "/users/{id}/valid", id)
                .retrieve()
                .body(Boolean.class);

        System.out.println(result + " : " + id + " existe");

        if (!result) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return true;
    }

    @PostMapping("/games")
    public void createGame(@RequestBody GameCreationParams params,
                           @RequestHeader("X-UserId") String userId) {

        System.out.println("Joueur : " + userId);
        System.out.println("Type de jeu : " + params.getGameType());
        System.out.println("Nombre de joueurs : " + params.getNumberOfPlayers());
        System.out.println("Taille du plateau : " + params.getBoardSize());
        System.out.println("Adversaires : " + params.getOpponentIds());

        checkUserExists(userId);
        gameService.createGame(params.getGameType(), userId, params.getOpponentIds());

        }

    @GetMapping("/games")
    public Stream<@NotNull UUID> getAllGame() {

        System.out.println("Test Get games");

        return gameDao.findAll().map(Game::getId);
    }

    @GetMapping("/gamesForUser")
    public Stream<@NotNull UUID> getAllGame(@RequestHeader("X-UserId") String userId) {

        System.out.println("Jeux pour l'utilisateur : " + userId);

        checkUserExists(userId);
        return gameDao.findByPlayerId(UUID.fromString(userId)).map(Game::getId);
    }

    @GetMapping("/games/{gameId}")
    public Optional<Game> getGame(@PathVariable UUID gameId,
                                  @RequestHeader("X-UserId") String userId) {

        System.out.println("Joueur : " + userId);
        System.out.println("Recherche de la partie : " + gameId);

        checkUserExists(userId);

        return gameDao.findById(String.valueOf(gameId)); // valueOf convertit l'UUID en String
    }

    @GetMapping("/games/{gameId}/tokens/{tokenId}/moves")
    public Set<CellPosition> getPossibleMoves(@PathVariable String gameId,
                                              @PathVariable String tokenId,
                                              @RequestHeader("X-UserId") String userId) {

        System.out.println("Joueur : " + userId);
        System.out.println("Partie : " + gameId); // exemple UUII : a9422d0f-ac5f-4578-8611-61756fe5dd5c
        System.out.println("Token : " + tokenId);

        checkUserExists(userId);

        return null;
    }


    @PostMapping("/games/{gameId}/moves")
    public void playMove(@PathVariable String gameId,
                         @RequestBody CellPosition position,
                         @RequestHeader("X-UserId") String userId) {

        System.out.println("Joueur : " + userId);
        System.out.println("Position choisie : " + position);
        System.out.println("Partie : " + gameId);

        checkUserExists(userId);
    }


}
