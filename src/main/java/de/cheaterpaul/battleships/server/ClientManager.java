package de.cheaterpaul.battleships.server;

import de.cheaterpaul.battleships.logic.client.AbstractClient;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ClientManager {
    private Map<UUID, AbstractClient> clients = new HashMap<>();

    public Map<UUID, AbstractClient> getClients() {
        return clients;
    }
}
