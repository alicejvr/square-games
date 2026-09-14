package com.square_games.demo;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

public interface GameCatalog {

    Map<String, String> getGames(Locale locale);

}
