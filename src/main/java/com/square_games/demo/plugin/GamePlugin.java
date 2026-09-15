package com.square_games.demo.plugin;

import fr.le_campus_numerique.square_games.engine.Game;
import java.util.Locale;

public interface GamePlugin {

    String getId();

    Game createGame();

    String getName(Locale locale);

}
