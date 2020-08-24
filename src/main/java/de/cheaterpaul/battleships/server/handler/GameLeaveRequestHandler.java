package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.logic.client.AbstractClient;
import de.cheaterpaul.battleships.logic.client.Client;
import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.notification.NotificationBuilder;
import de.cheaterpaul.battleships.network.message.request.GameLeaveRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Collection;

/**
 * MessageHandler for {@link GameLeaveRequest}
 */
public final class GameLeaveRequestHandler extends AbstractServerMessageHandler<GameLeaveRequest> {

    @Inject
    public GameLeaveRequestHandler(@Nonnull ClientManager clientManager, @Nonnull GameManager gameManager) {
        super(clientManager, gameManager, GameLeaveRequest.class);
    }

    @Override
    protected void handleMessage(GameLeaveRequest message, Id connectionId) throws GameException {
        AbstractClient client = this.clientManager.getClient(connectionId.getInt());
        switch (client.handleClientAs()) {
            case PLAYER:
                Collection<Client> players = this.gameManager.getGameHandlerForClientId(connectionId.getInt()).getPlayers();
                players.removeIf(player -> connectionId.getInt() == player.getId());
                this.clientManager.sendMessageToClients(NotificationBuilder.leaveNotification(connectionId.getInt()), players);
            case SPECTATOR:
                this.gameManager.removeClientFromGame(connectionId.getInt());
                this.clientManager.sendMessage(ResponseBuilder.gameLeaveResponse(), connectionId);
        }
    }
}
