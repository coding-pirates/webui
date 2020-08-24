package de.cheaterpaul.battleships.server;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import de.cheaterpaul.battleships.network.ConnectionHandler;
import de.cheaterpaul.battleships.network.message.request.TournamentParticipantsRequest;
import de.cheaterpaul.battleships.network.network.module.ServerNetworkModule;
import de.cheaterpaul.battleships.server.handler.*;
import de.cheaterpaul.battleships.server.util.ServerMarker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Network Module for guice for inserting mostly the same Object into every MessageHandler
 */
public final class ServerModule extends AbstractModule {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    protected void configure() {
        this.install(new ServerNetworkModule());

        LOGGER.info(ServerMarker.CONNECTION, "Binding server classes");

        bind(ConnectionHandler.class).to(de.cheaterpaul.battleships.server.ClientManager.class).in(Singleton.class);
        bind(de.cheaterpaul.battleships.server.ClientManager.class).in(Singleton.class);
        bind(de.cheaterpaul.battleships.server.GameManager.class).in(Singleton.class);
        bind(TournamentManager.class).in(Singleton.class);

        //bind all message handler to one instance
        bind(ConnectionClosedReportHandler.class).in(Singleton.class);
        bind(GameJoinPlayerRequestHandler.class).in(Singleton.class);
        bind(GameJoinSpectatorRequestHandler.class).in(Singleton.class);
        bind(LobbyRequestHandler.class).in(Singleton.class);
        bind(PlaceShipsRequestHandler.class).in(Singleton.class);
        bind(PlayerGameStateRequestHandler.class).in(Singleton.class);
        bind(PointsRequestHandler.class).in(Singleton.class);
        bind(RemainingTimeRequestHandler.class).in(Singleton.class);
        bind(ServerJoinRequestHandler.class).in(Singleton.class);
        bind(ShotsRequestHandler.class).in(Singleton.class);
        bind(SpectatorGameStateRequestHandler.class).in(Singleton.class);
        bind(TournamentGamesRequestHandler.class).in(Singleton.class);
        bind(TournamentParticipantsRequest.class).in(Singleton.class);
        bind(TournamentPointsRequestHandler.class).in(Singleton.class);
        bind(GameLeaveRequestHandler.class).in(Singleton.class);
    }
}
