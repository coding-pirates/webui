package de.upb.codingpirates.battleships.webui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.upb.codingpirates.battleships.network.dispatcher.MessageDispatcher;
import de.upb.codingpirates.battleships.server.ClientManager;
import de.upb.codingpirates.battleships.server.GameManager;
import de.upb.codingpirates.battleships.server.ServerModule;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    private final GameManager gameManager;
    private final ClientManager clientManager;

    public Configuration() {
        Injector injector = Guice.createInjector(new ServerModule());
        injector.getInstance(MessageDispatcher.class);
        gameManager = injector.getInstance(GameManager.class);
        clientManager = injector.getInstance(ClientManager.class);
    }

    @Bean
    public GameManager getGameManager() {
        return gameManager;
    }

    @Bean
    public ClientManager getClientManager() {
        return clientManager;
    }
}
