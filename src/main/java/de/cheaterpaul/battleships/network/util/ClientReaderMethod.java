package de.cheaterpaul.battleships.network.util;

import de.cheaterpaul.battleships.logic.util.Pair;
import de.cheaterpaul.battleships.network.Connection;
import de.cheaterpaul.battleships.network.exceptions.parser.ParserException;
import de.cheaterpaul.battleships.network.message.Message;
import de.cheaterpaul.battleships.network.message.report.ReportBuilder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.functions.Consumer;

import java.io.IOException;
import java.net.SocketException;

public abstract class ClientReaderMethod {

    /**
     * creates an observer for a connection and the specified consumer
     * and applies a Thread scheduler on the observable
     *
     * @param connection the connection to be observed
     * @param dispatch the consumer to consume a message
     * @param error the consumer to handle if an error occurs
     */
    abstract public void get(Connection connection, Consumer<Pair<Connection, Message>> dispatch, Consumer<Throwable> error);

    /**
     * creates an observer for a connection
     * @param connection the connection to be observed
     */
    protected Observable<Pair<Connection, Message>> create(Connection connection){
        return Observable.create((ObservableEmitter<Pair<Connection, Message>> emitter) -> {
            while (connection.isOpen()) {
                try {
                    Message message = connection.read();
                    emitter.onNext(new Pair<>(connection, message));
                } catch (SocketException e) {
                    connection.close();
                    emitter.onNext(new Pair<>(connection, ReportBuilder.connectionClosedReport()));
                } catch (IOException | ParserException e) {
                    emitter.onError(e);
                }

            }
        });
    }
}
