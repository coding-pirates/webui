package de.cheaterpaul.battleships.network.exceptions.game;

import de.cheaterpaul.battleships.logic.ErrorType;
import de.cheaterpaul.battleships.network.exceptions.BattleshipException;

import javax.annotation.Nonnull;

public abstract class GameException extends BattleshipException {
    @SuppressWarnings("WeakerAccess")
    protected GameException(@Nonnull String message, @Nonnull ErrorType errorType) {
        super(message, errorType);
    }
}
