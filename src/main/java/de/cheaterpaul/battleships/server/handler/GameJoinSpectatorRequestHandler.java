package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.logic.client.AbstractClient;
import de.cheaterpaul.battleships.logic.client.Client;
import de.cheaterpaul.battleships.logic.client.ClientType;
import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.GameJoinSpectatorRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;
import de.cheaterpaul.battleships.server.util.ServerMarker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * MessageHandler for {@link GameJoinSpectatorRequest}
 */
public final class GameJoinSpectatorRequestHandler extends AbstractServerMessageHandler<GameJoinSpectatorRequest> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    public GameJoinSpectatorRequestHandler(@Nonnull final ClientManager clientManager, @Nonnull final GameManager gameManager) {
        super(clientManager, gameManager, GameJoinSpectatorRequest.class);
    }

    @Override
    public void handleMessage(@Nonnull final GameJoinSpectatorRequest message, @Nonnull final Id connectionId) throws GameException {
        LOGGER.debug(ServerMarker.CLIENT, "Handling GameJoinSpectatorRequest from clientId {}, for gameId {}.", connectionId, message.getGameId());

        AbstractClient client = clientManager.getClient(connectionId.getInt());
        if (client.getClientType().equals(ClientType.PLAYER)) {
            ((Client) client).setSpectator(true);
        }

        gameManager.addClientToGame(message.getGameId(), client);
        clientManager.sendMessage(ResponseBuilder.gameJoinSpectatorResponse(message.getGameId()), clientManager.getClient(connectionId.getInt()));
    }
}
