package de.cheaterpaul.battleships.server.handler;

import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.request.TournamentParticipantsRequest;
import de.cheaterpaul.battleships.network.message.response.ResponseBuilder;
import de.cheaterpaul.battleships.server.ClientManager;
import de.cheaterpaul.battleships.server.GameManager;
import de.cheaterpaul.battleships.server.TournamentManager;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 * MessageHandler for {@link TournamentParticipantsRequest}
 */
public final class TournamentParticipantsRequestHandler extends AbstractServerMessageHandler<TournamentParticipantsRequest> {

    @Nonnull
    private final TournamentManager tournamentManager;

    @Inject
    public TournamentParticipantsRequestHandler(@Nonnull ClientManager clientManager, @Nonnull GameManager gameManager, @Nonnull TournamentManager tournamentManager) {
        super(clientManager, gameManager, TournamentParticipantsRequest.class);
        this.tournamentManager = tournamentManager;
    }

    @Override
    protected void handleMessage(TournamentParticipantsRequest message, Id connectionId) {
        this.clientManager.sendMessage(ResponseBuilder.tournamentParticipantsResponse(this.tournamentManager.isParticipating(connectionId.getInt())), connectionId);
    }
}
