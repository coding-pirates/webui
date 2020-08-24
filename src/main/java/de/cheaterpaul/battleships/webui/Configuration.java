package de.cheaterpaul.battleships.webui;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.cheaterpaul.battleships.network.dispatcher.MessageDispatcher;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;
import de.cheaterpaul.battleships.server.ServerModule;
import org.springframework.context.annotation.Bean;

import javax.annotation.Nonnull;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Nonnull
    private final Injector injector = Guice.createInjector(new ServerModule());

    private final GameManager gameManager;
    private final ClientManager clientManager;

    public Configuration() {
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
