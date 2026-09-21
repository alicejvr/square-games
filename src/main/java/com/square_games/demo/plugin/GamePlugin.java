package com.square_games.demo.plugin;

import fr.le_campus_numerique.square_games.engine.Game;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

public interface GamePlugin { // définit ce qu'un plugin de jeu doit savoir faire

    String getId();

    Game createGame();

    Game createGame(Set<UUID> playerIds);

    String getName(Locale locale);

}
