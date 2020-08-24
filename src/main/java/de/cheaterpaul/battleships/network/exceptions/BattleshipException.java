package de.cheaterpaul.battleships.network.exceptions;

import de.cheaterpaul.battleships.logic.ErrorType;
import de.cheaterpaul.battleships.network.id.Id;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class BattleshipException extends Exception {
    private final ErrorType errorType;
    @Nullable
    private Id connectionId;
    private int messageId;

    protected BattleshipException(@Nonnull String message, @Nonnull ErrorType errorType) {
        super(message);
        this.errorType = errorType;
    }

    @Nonnull
    public ErrorType getErrorType() {
        return errorType;
    }

    @Nullable
    public Id getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(@Nullable Id connectionId) {
        this.connectionId = connectionId;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
}
