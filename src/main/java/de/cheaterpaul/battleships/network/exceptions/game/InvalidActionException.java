package de.cheaterpaul.battleships.network.exceptions.game;

import de.cheaterpaul.battleships.logic.ErrorType;

import javax.annotation.Nonnull;

public class InvalidActionException extends GameException {
    public InvalidActionException(@Nonnull String message) {
        super(message, ErrorType.INVALID_ACTION);
    }
}
