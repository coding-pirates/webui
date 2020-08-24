package de.cheaterpaul.battleships.network.exceptions.game;

import de.cheaterpaul.battleships.logic.ErrorType;

public class NotAllowedException extends GameException {
    public NotAllowedException(String message) {
        super(message, ErrorType.NOT_ALLOWED);
    }
}
