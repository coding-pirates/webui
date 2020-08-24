package de.cheaterpaul.battleships.network.network;

import com.google.common.base.Preconditions;
import de.cheaterpaul.battleships.network.Connection;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.util.NetworkMarker;
import io.reactivex.Observable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.Socket;

/**
 * The ClientNetwork only stores the observer for incoming messages.
 *
 * @author Paul Becker
 */
public class ClientNetwork implements Network {
    private static final Logger LOGGER = LogManager.getLogger();

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private Observable<Connection> observer;

    /**
     * Creates a new Socket with the given parameters.
     * @param host host address
     * @param port battleships port on the server
     * @throws IOException if an IOException is occurring while connecting
     * @return created Connection
     */
    public Connection connect(@Nonnull String host, int port) throws IOException {
        Preconditions.checkNotNull(host);

        LOGGER.info(NetworkMarker.NETWORK,"trying to connect to {}:{}", host, port);
        return new Connection(new Id(0), new Socket(host, port));
    }

    /**
     * sets the given observer to an attribute
     * @param observer the observer to set
     */
    public void setObserver(Observable<Connection> observer) {
        this.observer = observer;
    }
}
