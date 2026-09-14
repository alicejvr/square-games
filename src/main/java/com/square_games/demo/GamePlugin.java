package com.square_games.demo;

import fr.le_campus_numerique.square_games.engine.Game;
import java.util.Locale;

public interface GamePlugin {

    Game createGame();

    String getName(Locale locale);

}
