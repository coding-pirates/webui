package de.cheaterpaul.battleships.network;

import de.cheaterpaul.battleships.logic.util.Pair;
import de.cheaterpaul.battleships.network.exceptions.BattleshipException;
import de.cheaterpaul.battleships.network.id.Id;
import de.cheaterpaul.battleships.network.message.Message;
import de.cheaterpaul.battleships.network.message.handler.MessageHandler;

/**
 * Interface for the ConnectionHandler on Server and Client for sending messages. Necessary for Guice to get a class depending on distribution.
 *
 * @author Paul Becker
 */
public interface ConnectionHandler {

    /**
     * called from {@link de.cheaterpaul.battleships.network.dispatcher.ServerMessageDispatcher#dispatch(Pair)} and {@link de.cheaterpaul.battleships.network.dispatcher.ClientMessageDispatcher#dispatch(Pair)} if a {@link BattleshipException} is thrown inside a {@link MessageHandler#handle(Message, Id)}
     *
     * @param e a BattleshipException which can be sent to the other distribution to show there failures
     */
    @SuppressWarnings("JavadocReference")
    void handleBattleshipException(BattleshipException e);
}
