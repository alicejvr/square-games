package com.square_games.demo.service;

import java.util.Locale;
import java.util.Map;

public interface GameCatalog {

    Map<String, String> getGames(Locale locale);

}
