package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.PointsRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Map;

/**
 * MessageHandler for {@link PointsRequest}
 */
public final class PointsRequestHandler extends AbstractServerMessageHandler<PointsRequest> {

    @Inject
    public PointsRequestHandler(@Nonnull final ClientManager clientManager,
                                @Nonnull final GameManager gameManager) {
        super(clientManager, gameManager, PointsRequest.class);
    }

    @Override
    public void handleMessage(PointsRequest message, Id connectionId) throws GameException {
        final Map<Integer, Integer> scores =
            gameManager
                .getGameHandlerForClientId(connectionId.getInt())
                .getScore();
        clientManager.sendMessage(ResponseBuilder.pointsResponse(scores), connectionId);
    }
}
