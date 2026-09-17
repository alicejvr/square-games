package com.square_games.demo.controller;

import com.square_games.demo.GameCreationParams;
import com.square_games.demo.dao.GameDao;
import com.square_games.demo.dao.InMemoryGameDao;
import com.square_games.demo.service.GameService;
import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@RestController
public class GameController {
    private final GameService gameService;
    private final GameDao gameDao;

    public GameController(GameService gameService, GameDao gameDao) {
        this.gameService = gameService;
        this.gameDao = gameDao;
    }

    @PostMapping("/games")
    public void createGame(@RequestBody GameCreationParams params) {

        System.out.println("Type de jeu : " + params.getGameType());
        System.out.println("Nombre de joueurs : " + params.getNumberOfPlayers());
        System.out.println("Taille du plateau : " + params.getBoardSize());

        gameService.createGame(params.getGameType());

        }

    @GetMapping("/games")
    public Stream<@NotNull UUID> getAllGame() {
        return gameDao.findAll().map(Game::getId);
    }

    @GetMapping("/games/{gameId}")
    public Optional<Game> getGame(@PathVariable UUID gameId) {
        System.out.println("Recherche de la partie : " + gameId);
        return gameDao.findById(String.valueOf(gameId)); // valueOf convertit l'UUID en String
    }

    @GetMapping("/games/{gameId}/tokens/{tokenId}/moves")
    public Set<CellPosition> getPossibleMoves(@PathVariable String gameId, @PathVariable String tokenId) {

        System.out.println("Partie : " + gameId); // exemple UUII : a9422d0f-ac5f-4578-8611-61756fe5dd5c
        System.out.println("Token : " + tokenId);

        return null;
    }


    @PostMapping("/games/{gameId}/moves")
    public void playMove(@PathVariable String gameId, @RequestBody CellPosition position) {

        System.out.println("Partie : " + gameId);
        System.out.println("Position choisie : " + position);
    }


}
