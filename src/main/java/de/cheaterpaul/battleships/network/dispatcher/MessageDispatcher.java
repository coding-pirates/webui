package de.cheaterpaul.battleships.network.dispatcher;

import com.google.inject.Injector;
import de.cheaterpaul.battleships.logic.util.Dist;
import de.cheaterpaul.battleships.logic.util.Pair;
import de.cheaterpaul.battleships.network.Connection;
import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.message.Message;
import de.cheaterpaul.battleships.network.message.handler.MessageHandler;
import de.cheaterpaul.battleships.network.scope.ConnectionScope;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Necessary for Guice to get a class depending on distribution.
 *
 * @author Paul Becker
 */
public interface MessageDispatcher {

    @SuppressWarnings("unchecked")
    default <T extends Message> void handleMessage(Pair<Connection, T> request, Dist dist, ConnectionScope scope, Injector injector, Logger LOGGER) throws GameException, ClassNotFoundException, IOException {
        String[] namespace = request.getValue().getClass().getName().split("\\.");
        String name = namespace[namespace.length - 1];
        Class<?> type;
        type = Class.forName(dist.getMessageHandlerPackage() + "." + name + "Handler");
        scope.seed(Connection.class, request.getKey());
        scope.enter(request.getKey().getId());

        MessageHandler<T> handler = (MessageHandler<T>) injector.getInstance(type);
        if (handler == null) {
            LOGGER.info("Can't find MessageHandler {} for Message {}", type, request.getValue().getClass());
        } else {
            if (handler.canHandle(request.getValue())) {
                handler.handle(request.getValue(), request.getKey().getId());
            }
        }
    }

}
