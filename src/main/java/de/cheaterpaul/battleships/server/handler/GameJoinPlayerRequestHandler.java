package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.logic.client.AbstractClient;
import de.cheaterpaul.battleships.logic.client.Client;
import de.cheaterpaul.battleships.logic.client.ClientType;
import de.cheaterpaul.battleships.logic.state.GameState;
import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.exceptions.game.NotAllowedException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.GameJoinPlayerRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;
import de.cheaterpaul.battleships.server.util.ServerMarker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * MessageHandler for {@link GameJoinPlayerRequest}
 */
public final class GameJoinPlayerRequestHandler extends AbstractServerMessageHandler<GameJoinPlayerRequest> {
    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    public GameJoinPlayerRequestHandler(@Nonnull final ClientManager clientManager,
                                        @Nonnull final GameManager   gameManager) {
        super(clientManager, gameManager, GameJoinPlayerRequest.class);
    }

    @Override
    public void handleMessage(@Nonnull final GameJoinPlayerRequest message, @Nonnull final Id connectionId) throws GameException {
        LOGGER.debug(ServerMarker.CLIENT, "Handling GameJoinPlayerRequest from clientId {}, for gameId {}.", connectionId, message.getGameId());

        if (!clientManager.getClientTypeFromID(connectionId.getInt()).equals(ClientType.PLAYER))
            throw new NotAllowedException("game.handler.gameJoinPlayerRequest.noPlayer");

        final GameState gameState = gameManager.getGameHandler(message.getGameId()).getState();

        switch (gameState) {
            case IN_PROGRESS:
                throw new NotAllowedException("game.handler.gameJoinPlayerRequest.gameAlreadyStarted");
            case FINISHED:
                throw new NotAllowedException("game.handler.gameJoinPlayerRequest.gameIsFinished");
        }

        final AbstractClient client = clientManager.getClient(connectionId.getInt());

        if (client.getClientType().equals(ClientType.PLAYER)) {
            ((Client) client).setSpectator(false);
        }

        final int gameId = message.getGameId();

        gameManager.addClientToGame(gameId, client);
        clientManager.sendMessage(ResponseBuilder.gameJoinPlayerResponse(gameId), client);
    }
}
