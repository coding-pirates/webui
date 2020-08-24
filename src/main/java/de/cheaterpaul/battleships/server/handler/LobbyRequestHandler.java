package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.logic.Game;
import de.cheaterpaul.battleships.network.exceptions.game.InvalidActionException;
import de.cheaterpaul.battleships.network.exceptions.game.NotAllowedException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.LobbyRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;
import de.cheaterpaul.battleships.server.game.GameHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MessageHandler for {@link LobbyRequest}
 */
public final class LobbyRequestHandler extends AbstractServerMessageHandler<LobbyRequest> {

    private static final Logger LOGGER = LogManager.getLogger();

    @Inject
    public LobbyRequestHandler(@Nonnull final ClientManager clientManager, @Nonnull final GameManager gameManager) {
        super(clientManager, gameManager, LobbyRequest.class);
    }

    @Override
    public void handleMessage(@Nonnull final LobbyRequest message, @Nonnull final Id connectionId) throws NotAllowedException, InvalidActionException {
        LOGGER.debug("Handling LobbyRequest for clientId {}.", connectionId);


        if (!this.clientManager.existsClient(connectionId.getInt()))
            throw new NotAllowedException("game.handler.lobbyRequestHandler.notRegistered");

        final List<Game> games =
                this.gameManager
                        .getGameHandlers()
                        .stream()
                        .map(GameHandler::getGame)
                        .collect(Collectors.toList());

        this.clientManager.sendMessage(ResponseBuilder.lobbyResponse(games), connectionId);
    }
}
