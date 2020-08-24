package de.cheaterpaul.battleships.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.cheaterpaul.battleships.logic.*;
import de.cheaterpaul.battleships.logic.client.AbstractClient;
import de.cheaterpaul.battleships.logic.client.ClientType;
import de.cheaterpaul.battleships.logic.state.GameState;
import de.cheaterpaul.battleships.logic.util.Pair;
import de.cheaterpaul.battleships.network.exceptions.game.GameException;
import de.cheaterpaul.battleships.network.exceptions.game.InvalidActionException;
import de.cheaterpaul.battleships.network.exceptions.game.NotAllowedException;
import de.cheaterpaul.battleships.network.id.IdManager;
import de.cheaterpaul.battleships.server.game.GameHandler;
import de.cheaterpaul.battleships.server.game.TournamentHandler;
import de.cheaterpaul.battleships.server.util.ServerMarker;
import de.cheaterpaul.battleships.server.util.ServerProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.*;

/**
 * Handles all {@link Game}-related functionality.
 *
 * @author Paul Becker
 */
public class GameManager {
    private static final Logger LOGGER = LogManager.getLogger();

    @Nonnull
    private final de.cheaterpaul.battleships.server.ClientManager clientManager;
    @Nonnull
    private final IdManager idManager;

    /**
     * maps game id to gamehandler
     */
    private final Map<Integer, GameHandler> gameHandlersById = new HashMap<>();
    /**
     * maps client id to gameid
     */
    private final Map<Integer, Integer> clientToGame = Collections.synchronizedMap(Maps.newHashMap());

    private final List<Pair<Integer, Long>> removeGames = Collections.synchronizedList(Lists.newArrayList());

    @Inject
    public GameManager(@Nonnull de.cheaterpaul.battleships.server.ClientManager handler, @Nonnull IdManager idManager) {
        this.clientManager = handler;
        this.idManager = idManager;
        new Timer("Server Main").schedule(new TimerTask() {
            @Override
            public void run() {
                GameManager.this.run();
            }
        }, 1L, 1L);
    }

    /**
     * creates game based on parameter
     *
     * @param configuration
     * @param name
     * @param tournamentHandler
     * @return {@code -1} if game was created successful, {@code > 0} if the selected field size of the Configuration is too small
     */
    public GameHandler createGame(@Nonnull Configuration configuration, @Nonnull String name, @Nullable TournamentHandler tournamentHandler) {
        int id = this.idManager.generate().getInt();
        LOGGER.debug(ServerMarker.GAME, "Create game: {} with id: {}", name, id);
        GameHandler gameHandler = new GameHandler(name, id, configuration, tournamentHandler != null, clientManager, this, tournamentHandler);
        this.gameHandlersById.put(id, gameHandler);
        return gameHandler;
    }

    public GameHandler createGame(@Nonnull Configuration configuration, @Nonnull String name) {
        return createGame(configuration, name, null);
    }

    /**
     * adds client with clientType to the specific game
     *
     * @param gameId
     * @param client
     * @throws InvalidActionException if game does not exist
     */
    public void addClientToGame(int gameId, @Nonnull AbstractClient client) throws GameException {
        LOGGER.debug(ServerMarker.GAME, "Adding client {}, with type {}, to game {}", client.getId(), client.getClientType(), gameId);
        if(this.clientToGame.containsKey(client.getId())){
            if (client.getClientType().equals(ClientType.PLAYER) && client.handleClientAs().equals(ClientType.PLAYER)) {
                GameHandler handler = this.gameHandlersById.get(this.clientToGame.get(client.getId()));
                if (handler.getState().equals(GameState.FINISHED)) {
                    this.clientToGame.remove(client.getId());
                }
                throw new NotAllowedException("game.gameManager.alreadyIngame");
            } else {
                this.clientToGame.remove(client.getId());
            }
        }
        if (this.gameHandlersById.containsKey(gameId)) {
            this.gameHandlersById.get(gameId).addClient(client);
            this.clientToGame.put(client.getId(), gameId);
        } else {
            LOGGER.error(ServerMarker.GAME, "Can't find game {}", gameId);
            throw new InvalidActionException("game.gameManager.noGame");
        }
        //Todo remove call
        if (this.getGameHandler(gameId).getGame().getCurrentPlayerCount() >= this.getGameHandler(gameId).getGame().getMaxPlayerCount()) {
            launchGame(gameId);
        }
    }

    /**
     * removes client from participating games
     *
     * @param client
     * @throws InvalidActionException if client does not participate
     */
    public void removeClientFromGame(int client) throws InvalidActionException {
        LOGGER.debug(ServerMarker.CLIENT, "Remove client {} from active game", client);
        if (clientToGame.containsKey(client)) {
            this.gameHandlersById.get(this.clientToGame.remove(client)).removeClient(client);
        } else {
            LOGGER.warn(ServerMarker.CLIENT, "Client {} does not participate in a game", client);
            throw new InvalidActionException("game.gameManager.noGameForClient");
        }
    }

    /**
     * launches game with id
     *
     * @param gameId gameId
     * @return {@code false} if player count is under 2
     */
    public boolean launchGame(int gameId) {
        LOGGER.debug(ServerMarker.GAME, "launched game {}, {}", gameId, this.gameHandlersById.get(gameId).getGame().getName());
        return this.gameHandlersById.get(gameId).launchGame();
    }

    /**
     * pauses game with id
     *
     * @param gameId gameId
     */
    public void pauseGame(int gameId) {
        this.gameHandlersById.get(gameId).pauseGame();
    }

    /**
     * continue game with id
     *
     * @param gameId gameId
     */
    public void continueGame(int gameId) {
        this.gameHandlersById.get(gameId).continueGame();
    }

    /**
     * continue game with id
     *
     * @param gameId gameId
     * @param points if {@code false} all points will be set to 0
     */
    public void abortGame(int gameId, boolean points) {
        this.gameHandlersById.get(gameId).abortGame(points);
    }

    /**
     * @return all existing games
     */
    public Collection<GameHandler> getGameHandlers() {
        return this.gameHandlersById.values();
    }

    /**
     * @param clientId for which a game should be found
     * @return a game where the client participate
     * @throws InvalidActionException if the client does not participate in a game
     */
    @Nonnull
    public GameHandler getGameHandlerForClientId(int clientId) throws InvalidActionException {
        if (!this.clientToGame.containsKey(clientId)) {
            LOGGER.warn(ServerMarker.CLIENT, "Could not get game for client {}", clientId);
            throw new InvalidActionException("game.gameManager.noGameForClient");
        }
        return this.gameHandlersById.get(this.clientToGame.get(clientId));
    }

    /**
     * @param id id of the game
     * @return the game with the id
     * @throws InvalidActionException if the game does not exist
     */
    @Nonnull
    public GameHandler getGameHandler(int id) throws InvalidActionException {
        if (!this.gameHandlersById.containsKey(id)) {
            LOGGER.warn(ServerMarker.GAME, "The game with id: {} does not exist", id);
            throw new InvalidActionException("game.gameManager.gameNotExist");
        }
        return this.gameHandlersById.get(id);
    }

    public void gameFinished(int id){
        if(!gameHandlersById.containsKey(id)){
            LOGGER.debug(ServerMarker.GAME,"Could not finish game {}, can't find it", id);
            return;
        }
        if(gameHandlersById.get(id).getGame().isTournament()){

        }
        this.gameHandlersById.get(id).getAllClients().forEach(client -> this.clientToGame.remove(client.getId()));
        this.removeGames.add(Pair.of(id, System.currentTimeMillis() + ServerProperties.MAX_FINISHED_GAME_EXIST_TIME));
    }

    public void removeGames(){
        long time = System.currentTimeMillis();
        this.removeGames.removeIf(integerLongPair -> {
            if(integerLongPair.getValue() <= time && !gameHandlersById.get(integerLongPair.getKey()).getGame().isTournament()){
                gameHandlersById.remove(integerLongPair.getKey());
                return true;
            }
            return false;
        });
    }

    /**
     * run method for every game
     */
    private void run() {
        this.gameHandlersById.values().forEach(GameHandler::run);

        if (System.currentTimeMillis() % 10_000 == 0)
            removeGames();
    }

    public Map<Integer, GameHandler> getGameMappings() {
        return gameHandlersById;
    }
}
