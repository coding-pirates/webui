package de.cheaterpaul.battleships.network.message.response;

import de.cheaterpaul.battleships.network.message.Message;

/**
 * Sent by the server to the client when ship placement is accepted.
 *
 * @author Interdoc committee, Paul Becker
 */
public class PlaceShipsResponse extends Message {

    /**
     * Message id of {@link PlaceShipsResponse}
     */
    public static final int MESSAGE_ID = 351;

    PlaceShipsResponse() {
        super(MESSAGE_ID);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        return obj instanceof PlaceShipsResponse;
    }
}
