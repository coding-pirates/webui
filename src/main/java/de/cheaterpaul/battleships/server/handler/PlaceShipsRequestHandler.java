package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.logic.client.AbstractClient;
import de.cheaterpaul.battleships.logic.client.Client;
import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.exceptions.game.NotAllowedException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.PlaceShipsRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;
import de.cheaterpaul.battleships.server.util.ServerMarker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * MessageHandler for {@link PlaceShipsRequest}
 */
public final class PlaceShipsRequestHandler extends AbstractServerMessageHandler<PlaceShipsRequest> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    public PlaceShipsRequestHandler(@Nonnull final ClientManager clientManager, @Nonnull final GameManager gameManager) {
        super(clientManager, gameManager, PlaceShipsRequest.class);
    }

    @Override
    public void handleMessage(final @Nonnull PlaceShipsRequest message, final @Nonnull Id connectionId) throws GameException {
        LOGGER.debug(ServerMarker.HANDLER, "Handling PlaceShipsRequest from clientId {}.", connectionId.getInt());

        AbstractClient client = clientManager.getClient(connectionId.getInt());

        switch (client.handleClientAs()) {
            case PLAYER:
                if (!((Client) client).isDead()) {
                    gameManager.getGameHandlerForClientId(connectionId.getInt()).addShipPlacement(connectionId.getInt(), message.getPositions());
                    clientManager.sendMessage(ResponseBuilder.placeShipsResponse(), connectionId);
                    break;
                }
            case SPECTATOR:
                throw new NotAllowedException("game.handler.gameJoinPlayerRequest.noPlayer");
        }
    }
}
