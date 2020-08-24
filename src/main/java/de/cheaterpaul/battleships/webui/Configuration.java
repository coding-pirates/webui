package de.cheaterpaul.battleships.webui;

import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    private GameManager gameManager;
    private ClientManager clientManager;

    @Bean
    public GameManager getGameManager() {
        return gameManager != null ? gameManager : (gameManager = new GameManager());
    }

    @Bean
    public ClientManager getClientManager() {
        return clientManager != null ? clientManager : (clientManager = new ClientManager());
    }
}
