package de.cheaterpaul.battleships.server.exceptions;

import de.cheaterpaul.battleships.network.exceptions.game.InvalidActionException;

/**
 * should be thrown when client should be added to a gabe, but it is full
 */
public class GameFullExeption extends InvalidActionException {
    public GameFullExeption() {
        super("game.isFull");
    }
}
