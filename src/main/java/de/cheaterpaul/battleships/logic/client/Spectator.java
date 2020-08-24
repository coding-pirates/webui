package de.cheaterpaul.battleships.logic.client;

import javax.annotation.Nonnull;

public class Spectator extends AbstractClient {

    public Spectator(int id, @Nonnull String name) {
        super(id, name);
    }

    @Nonnull
    @Override
    public ClientType getClientType() {
        return ClientType.SPECTATOR;
    }

    @Nonnull
    @Override
    public ClientType handleClientAs() {
        return ClientType.SPECTATOR;
    }

}
