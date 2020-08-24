package de.cheaterpaul.battleships.server;

import de.cheaterpaul.battleships.logic.Game;

import java.util.HashSet;
import java.util.Set;

public class GameManager {

    private final Set<Game> games = new HashSet<>();

    public Set<Game> getGames() {
        return games;
    }
}
