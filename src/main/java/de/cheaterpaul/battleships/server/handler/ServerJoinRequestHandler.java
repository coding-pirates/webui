package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.ServerJoinRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * MessageHandler for {@link ServerJoinRequest}
 */
public final class ServerJoinRequestHandler extends AbstractServerMessageHandler<ServerJoinRequest> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    public ServerJoinRequestHandler(@Nonnull final ClientManager clientManager,
                                    @Nonnull final GameManager gameManager) {
        super(clientManager, gameManager, ServerJoinRequest.class);
    }

    @Override
    public void handleMessage(@Nonnull final ServerJoinRequest message,
                              @Nonnull final Id connectionId) throws GameException {
        LOGGER.debug("Handling ServerJoinRequest from clientId {}, with name '{}', as type {}.", connectionId, message.getName(), message.getClientType());

        clientManager.create(connectionId.getInt(), message.getName(), message.getClientType());
        clientManager.sendMessage(ResponseBuilder.serverJoinResponse(connectionId.getInt()), connectionId);
    }
}
