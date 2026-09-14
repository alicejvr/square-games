package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.CellPosition;
import fr.le_campus_numerique.square_games.engine.Game;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/games")
    public void createGame(@RequestBody GameCreationParams params) {

        System.out.println("Type de jeu : " + params.getGameType());
        System.out.println("Nombre de joueurs : " + params.getNumberOfPlayers());
        System.out.println("Taille du plateau : " + params.getBoardSize());
        }

    @GetMapping("/games/{gameId}")
    public Game getGame(@PathVariable UUID gameId) {
        System.out.println("Recherche de la partie : " + gameId);
        return null;
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
