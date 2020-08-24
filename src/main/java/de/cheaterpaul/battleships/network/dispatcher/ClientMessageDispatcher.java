package de.cheaterpaul.battleships.network.dispatcher;

import com.google.inject.Inject;
import com.google.inject.Injector;
import de.cheaterpaul.battleships.logic.util.Dist;
import de.cheaterpaul.battleships.logic.util.Pair;
import de.cheaterpaul.battleships.network.Connection;
import de.cheaterpaul.battleships.network.ConnectionHandler;
import de.cheaterpaul.battleships.network.annotations.bindings.CachedThreadPool;
import de.cheaterpaul.battleships.network.exceptions.BattleshipException;
import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.message.Message;
import de.cheaterpaul.battleships.network.message.handler.MessageHandler;
import de.cheaterpaul.battleships.network.network.ClientNetwork;
import de.cheaterpaul.battleships.network.scope.ConnectionScope;
import de.cheaterpaul.battleships.network.util.ClientReaderMethod;
import de.cheaterpaul.battleships.network.util.NetworkMarker;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * The {@link ClientMessageDispatcher} registers a read loop for the {@link Observable} of the {@link ClientNetwork}. The read loop {@link ClientMessageDispatcher#dispatch(Pair)} a request if it receives a message.
 *
 * @author Paul Becker
 */
public class ClientMessageDispatcher implements MessageDispatcher {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ClientNetwork network;
    private final Scheduler scheduler;
    private final ExecutorService executorService;
    private final ConnectionScope scope;
    private final Injector injector;
    @Nullable
    private Connection connection;
    @Inject
    private ConnectionHandler connectionHandler;
    @Inject
    private ClientReaderMethod readLoop;

    @Inject
    public ClientMessageDispatcher(@CachedThreadPool ExecutorService executorService, Injector injector, ConnectionScope scope, ClientNetwork clientNetwork) {
        this.scheduler = Schedulers.from(executorService);
        this.executorService = executorService;
        this.scope = scope;
        this.network = clientNetwork;
        this.injector = injector;
    }

    public void connect(@Nonnull String host, int port) throws IOException {
        this.connection = this.network.connect(host, port);
        Observable<Connection> observer = Observable.create(this::setConnection);

        //noinspection ResultOfMethodCallIgnored
        observer.subscribeOn(this.scheduler).subscribe(this::readLoop);
        this.network.setObserver(observer);
    }

    private void readLoop(Connection connection) {
        LOGGER.info(NetworkMarker.CONNECTION,"Connection from " + connection.getInetAdress());
        readLoop.get(connection, this::dispatch, this::error);
    }

    /**
     * Called if the {@link Observable} gets a message.
     * <br>
     * It tries to get a {@link MessageHandler} based on the name of the message and tries to let the MessageHandler handle the message. Otherwise logs th failure.
     *
     * @param request a {@link Pair} of a Message and its Connection
     */
    private void dispatch(Pair<Connection, Message> request) {
        try {
            handleMessage(request, Dist.CLIENT, this.scope, this.injector, LOGGER);
        } catch (ClassNotFoundException e) {
            LOGGER.error(NetworkMarker.MESSAGE, "Can't find MessageHandler for Message", e);
        } catch (GameException e) {
            this.connectionHandler.handleBattleshipException(e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.scope.exit();
        }
    }

    private void error(Throwable throwable) {
        if (throwable instanceof BattleshipException)
            this.connectionHandler.handleBattleshipException((BattleshipException) throwable);
        else {
            LOGGER.error(NetworkMarker.MESSAGE,"Error while reading Messages on Server", throwable);
        }
    }

    /**
     * This sets an Observable to observe only the connection from the server.
     * @param emitter observable emitter
     */
    private void setConnection(ObservableEmitter<Connection> emitter) {
        emitter.onNext(this.connection);
    }

    @Nullable
    public Connection getConnection(){
        return this.connection;
    }
}
