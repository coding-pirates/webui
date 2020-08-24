package de.cheaterpaul.battleships.network.connectionmanager;

import com.google.inject.Inject;
import de.cheaterpaul.battleships.network.Connection;
import de.cheaterpaul.battleships.network.annotations.bindings.CachedThreadPool;
import de.cheaterpaul.battleships.network.dispatcher.ClientMessageDispatcher;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.Message;
import de.cheaterpaul.battleships.network.util.NetworkMarker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * @author Paul Becker
 */
public class ClientConnectionManager {
    private static final Logger LOGGER = LogManager.getLogger();
    @Nonnull
    private final ClientMessageDispatcher messageDispatcher;
    @Nonnull
    private final ExecutorService executorService;

    @Inject
    public ClientConnectionManager(@Nonnull @CachedThreadPool ExecutorService executorService, @Nonnull ClientMessageDispatcher messageDispatcher) {
        this.messageDispatcher = messageDispatcher;
        this.executorService = executorService;
    }

    /**
     * connects to the server
     *
     * @param host hostname of the server
     * @param port port of the server
     */
    public void create(@Nonnull String host, int port) {
        this.create(host, port, null, null);
    }

    /**
     * connects to the server
     *
     * @param host hostname of the server
     * @param port port of the server
     * @param onSuccess task to run if the connect is successful
     * @param onFailure task to run if the connect has failed
     */
    public void create(@Nonnull String host, int port, @Nullable Runnable onSuccess, @Nullable Runnable onFailure) {
        executorService.submit(() -> {
            try {
                this.messageDispatcher.connect(host, port);
                if(onSuccess != null)
                    onSuccess.run();
            } catch (IOException e) {
                LOGGER.warn(NetworkMarker.NETWORK, "Could not connect",e);
                if(onFailure != null)
                    onFailure.run();
            }
        });
    }

    /**
     * @return the {@link Connection} to the server
     */
    @Nullable
    public Connection getConnection() {
        return this.messageDispatcher.getConnection();
    }

    /**
     * sends message to the server
     * @param message {@link Message} to send
     */
    public void send(Message message) {
        executorService.submit(() ->{
            try {
                if(this.getConnection() == null){
                    LOGGER.warn(NetworkMarker.NETWORK,"Client connection is not established");
                    return;
                }
                getConnection().send(message);
                LOGGER.debug(NetworkMarker.MESSAGE, "Send message {}", message);
            } catch (Exception e) {
                LOGGER.warn(NetworkMarker.NETWORK, "Could not send Message " + message, e);
            }
        });
    }

    /**
     * disconnects the client
     */
    public void disconnect(){
        this.disconnect(null, null);
    }

    /**
     * disconnects the client
     * @param onSuccess task to run if the disconnect is successful
     * @param onFailure task to run if the disconnect has failed
     */
    public void disconnect(Runnable onSuccess, Runnable onFailure){
        this.executorService.submit(() -> {
            try {
                if(this.getConnection() == null){
                    LOGGER.warn(NetworkMarker.NETWORK,"Client connection is not established");
                    return;
                }
                getConnection().close();
                if(onSuccess != null)
                    onSuccess.run();
            } catch (IOException e) {
                LOGGER.warn(NetworkMarker.NETWORK, "Could not close the connection", e);
                if(onFailure != null)
                    onFailure.run();
            }
        });
    }

    /**
     * sets connection id to new id
     *
     * @param id new Id
     */
    public void setConnectionId(int id) {
        if(this.getConnection() != null)
            this.getConnection().setId(new Id(id));
    }
}
