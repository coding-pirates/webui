package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.logic.client.AbstractClient;
import de.cheaterpaul.battleships.logic.client.Client;
import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.exceptions.game.NotAllowedException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.ShotsRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * MessageHandler for {@link ShotsRequest}
 */
public final class ShotsRequestHandler extends AbstractServerMessageHandler<ShotsRequest> {

    @Inject
    public ShotsRequestHandler(@Nonnull final ClientManager clientManager, @Nonnull final GameManager gameManager) {
        super(clientManager, gameManager, ShotsRequest.class);
    }

    @Override
    public void handleMessage(@Nonnull final ShotsRequest message, @Nonnull final Id connectionId) throws GameException {

        AbstractClient client = clientManager.getClient(connectionId.getInt());

        switch (client.handleClientAs()) {
            case PLAYER:
                if (!((Client) client).isDead()) {
                    gameManager
                            .getGameHandlerForClientId(connectionId.getInt())
                            .addShotPlacement(connectionId.getInt(), message.getShots());
                    clientManager.sendMessage(ResponseBuilder.shotsResponse(), connectionId);
                    break;
                }
            case SPECTATOR:
                throw new NotAllowedException("game.handler.gameJoinPlayerRequest.noPlayer");
        }
    }
}
