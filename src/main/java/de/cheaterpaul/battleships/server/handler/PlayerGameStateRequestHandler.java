package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.logic.client.AbstractClient;
import de.cheaterpaul.battleships.logic.client.Client;
import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.exceptions.game.NotAllowedException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.PlayerGameStateRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;
import de.cheaterpaul.battleships.server.game.GameHandler;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * MessageHandler for {@link PlayerGameStateRequest}
 */
public final class PlayerGameStateRequestHandler extends AbstractServerMessageHandler<PlayerGameStateRequest> {

    @Inject
    public PlayerGameStateRequestHandler(@Nonnull final ClientManager clientManager,
                                         @Nonnull final GameManager gameManager) {
        super(clientManager, gameManager, PlayerGameStateRequest.class);
    }

    @Override
    public void handleMessage(@Nonnull final PlayerGameStateRequest message, @Nonnull final Id connectionId) throws GameException {

        AbstractClient client = clientManager.getClient(connectionId.getInt());

        switch (client.handleClientAs()) {
            case PLAYER:
                if (!((Client) client).isDead()) {
                    final GameHandler handler = gameManager.getGameHandlerForClientId(connectionId.getInt());

                    clientManager.sendMessage(
                            ResponseBuilder.playerGameStateResponse()
                                    .gameState(handler.getState())
                                    .hits(handler.getHitShots())
                                    .sunk(handler.getSunkShots())
                                    .ships(handler.getStartShip().get(connectionId.getInt()))
                                    .players(handler.getPlayers())
                                    .build(),
                            connectionId);
                    break;
                }
            case SPECTATOR:
                throw new NotAllowedException("game.handler.gameJoinPlayerRequest.noPlayer");
        }
    }
}
