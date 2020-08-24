package de.cheaterpaul.battleships.network.message.response;

import de.cheaterpaul.battleships.logic.Game;
import de.cheaterpaul.battleships.network.message.Message;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Is the answer of the server on a LobbyRequest and contains all currently existing games
 * of the server.
 *
 * @author Interdoc committee, Paul Becker
 */
public class LobbyResponse extends Message {

    /**
     * Message id of {@link LobbyResponse}
     */
    public static final int MESSAGE_ID = 251;

    /**
     * A collection, the game
     * Objects of all past, current
     * and planned games
     */
    @Nonnull
    private final Collection<Game> games;

    LobbyResponse(@Nonnull Collection<Game> games) {
        super(MESSAGE_ID);
        this.games = games;
    }

    /**
     * @return {@link #games}
     */
    @Nonnull
    public Collection<Game> getGames() {
        return games;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if (obj instanceof LobbyResponse){
            return games.equals(((LobbyResponse)obj).games);
        }
        return false;
    }
}
