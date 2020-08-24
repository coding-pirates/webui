package de.cheaterpaul.battleships.network.message.notification;

import com.google.gson.annotations.SerializedName;
import de.cheaterpaul.battleships.logic.client.Client;
import de.cheaterpaul.battleships.logic.Configuration;
import de.cheaterpaul.battleships.network.message.Message;

import javax.annotation.Nonnull;
import java.util.Collection;

/**
 * Is sent from the server to the client to notify the client that now all players
 * joined and the placement of the ships can be started.
 *
 * @author Interdoc committee, Paul Becker
 */
public class GameInitNotification extends Message {

    /**
     * Message id of {@link GameInitNotification}
     */
    public static final int MESSAGE_ID = 366;

    /**
     * A collection about client,
     * the all participating clients of the
     * Game contains
     */
    @Nonnull
    private final Collection<Client> clientList;

    /**
     * The selected {@link Configuration} for the game
     */
    @Nonnull
    @SerializedName("config")
    private final Configuration configuration;


    GameInitNotification(@Nonnull Collection<Client> clientList, @Nonnull Configuration configuration) {
        super(MESSAGE_ID);
        this.clientList = clientList;
        this.configuration = configuration;
    }

    /**
     * @return {@link #clientList}
     */
    @Nonnull
    public Collection<Client> getClientList() {
        return clientList;
    }

    /**
     * @return {@link #configuration}
     */
    @Nonnull
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof GameInitNotification){
            return clientList.equals(((GameInitNotification)obj).clientList) && configuration.equals(((GameInitNotification)obj).configuration);
        }
        return false;
    }
}
