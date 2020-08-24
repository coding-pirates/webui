package de.cheaterpaul.battleships.server.util;

import de.cheaterpaul.battleships.logic.client.AbstractClient;
import de.cheaterpaul.battleships.logic.client.Client;

/**
 * Settings used by the server
 */
public class ServerProperties {

    /**
     * The time a finished {@link de.cheaterpaul.battleships.logic.Game} should be available
     * before it will be removed
     *
     * @see de.cheaterpaul.battleships.server.GameManager#removeGames()
     */
    public static final long MAX_FINISHED_GAME_EXIST_TIME = 300000;

    /**
     * The minimum amount of {@link Client.Client}s with {@link Client.ClientType#PLAYER} required in order to launch a game using
     * the {@link de.cheaterpaul.battleships.server.game.GameHandler#launchGame()} method.
     *
     * @see de.cheaterpaul.battleships.server.game.GameHandler#launchGame()
     */
    public static final int MIN_PLAYER_COUNT = 2;

    /**
     * The maximum amount of {@link Client.Client}s with {@link Client.ClientType#SPECTATOR} which can spectate a {@link de.cheaterpaul.battleships.logic.Game}.
     *
     * @see de.cheaterpaul.battleships.server.game.GameHandler#addClient(AbstractClient)
     */
    public static final int MAX_SPECTATOR_COUNT = Integer.MAX_VALUE;

    public static final long TOURNAMENT_GAMEFINISH_TIME = 10000;

    public static final long AUTO_GAME_START = 20000;
}
