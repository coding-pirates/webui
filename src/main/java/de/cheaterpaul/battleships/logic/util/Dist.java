package de.cheaterpaul.battleships.logic.util;

import javax.annotation.Nonnull;

public enum Dist {
    SERVER("de.cheaterpaul.battleships.server.handler"),
    CLIENT("de.cheaterpaul.battleships.client.handler");

    private final String messageHandlerPackage;

    Dist(@Nonnull final String messageHandlerPackage) {
        this.messageHandlerPackage = messageHandlerPackage;
    }

    public String getMessageHandlerPackage() {
        return messageHandlerPackage;
    }
}
