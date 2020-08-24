package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.RemainingTimeRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * MessageHandler for {@link RemainingTimeRequest}
 */
public final class RemainingTimeRequestHandler extends AbstractServerMessageHandler<RemainingTimeRequest> {

    @Inject
    public RemainingTimeRequestHandler(final @Nonnull ClientManager clientManager,
                                       final @Nonnull GameManager gameManager) {
        super(clientManager, gameManager, RemainingTimeRequest.class);
    }

    @Override
    public void handleMessage(@Nonnull final RemainingTimeRequest message,
                              @Nonnull final Id connectionId) throws GameException {
        final long remainingTime =
            gameManager
                .getGameHandlerForClientId(connectionId.getInt())
                .getRemainingTime();

        clientManager.sendMessage(ResponseBuilder.remainingTimeResponse(remainingTime), connectionId);
    }
}
