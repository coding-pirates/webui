package de.cheaterpaul.battleships.logic;

import com.google.common.base.Objects;
import de.cheaterpaul.battleships.logic.state.GameState;

import javax.annotation.Nonnull;

/**
 * Represents a single game.
 *
 * @author Interdoc committee
 * @author Paul Becker
 * @author Andre Blanke
 */
public final class Game {

    /**
     * The name of this {@code Game} set by the host at creation time.
     */
    @Nonnull
    private String name;

    /**
     * The unique ID of this {@code Game}.
     */
    private int id;

    /**
     * Contains the number of currently registered
     * player
     */
    private int currentPlayerCount;

    /**
     * Contains the status of the game
     */
    @Nonnull
    private GameState state;

    /**
     * Contains the {@link de.cheaterpaul.battleships.logic.Configuration} associated with this {@code Game}.
     */
    @Nonnull
    private de.cheaterpaul.battleships.logic.Configuration config;

    /**
     * Indicates whether the game belongs to a tournament.
     */
    private boolean tournament;

    /**
     * Instantiates a new {@code Game} object.
     *
     * @param id The integer responsible for uniquely identifying this {@code Game}.
     *
     * @param name The name of this {@code Game}, e.g. for display in the user interface.
     *
     * @param state The initial {@link GameState} of the {@code Game} instance to be created.
     *
     * @param config The {@link de.cheaterpaul.battleships.logic.Configuration} specifying the behaviour of this instance.
     *
     * @param tournament Whether this {@code Game} is part of a tournament.
     */
    public Game(
            final int id,
            @Nonnull final String        name,
            @Nonnull final GameState     state,
            @Nonnull final de.cheaterpaul.battleships.logic.Configuration config,
            final boolean tournament) {
        this.name       = name;
        this.id         = id;
        this.state      = state;
        this.config     = config;
        this.tournament = tournament;
    }

    /**
     * "Synthetic" getter acting as a delegate to {@link de.cheaterpaul.battleships.logic.Configuration#maxPlayerCount}.
     *
     * This getter is required because JavaFX's {@code PropertyValueFactory}, which is used to access the property
     * values of {@code Game} instances for display in a {@code TableView}, is not able to handle nested properties
     * (e.g. {@code config.maxPlayerCount}).
     *
     * @return The maximum amount of players supported by this {@code Game} instance according to its {@link #config}.
     */
    public int getMaxPlayerCount() {
        return config.maxPlayerCount;
    }

    /**
     * Returns the unique ID associated with this {@code Game}.
     *
     * @return {@link #id}
     */
    public int getId() {
        return id;
    }

    /**
     * Return the name of the {@code Game}, e.g. for display in the user interface.
     *
     * @return {@link #name}
     */
    @Nonnull
    public String getName() {
        return name;
    }

    @Nonnull
    public de.cheaterpaul.battleships.logic.Configuration getConfig() {
        return config;
    }

    /**
     * Returns whether this {@code Game} is part of a tournament.
     *
     * @return {@code true} if this {@code Game} is part of a tournament, otherwise {@code false}.
     */
    public boolean isTournament() {
        return tournament;
    }

    /**
     * Return current amount of players who are playing this game.
     *
     * @return {@link #currentPlayerCount}
     */
    public int getCurrentPlayerCount() {
        return currentPlayerCount;
    }

    public void setCurrentPlayerCount(final int currentPlayerCount) {
        if (currentPlayerCount < 0)
            throw new IllegalArgumentException("currentPlayerCount cannot be less than zero.");
        this.currentPlayerCount = currentPlayerCount;
    }

    /**
     * Return status of the {@code Game}.
     *
     * @return {@link #state}
     */
    @Nonnull
    public GameState getState() {
        return state;
    }

    public void setState(@Nonnull final GameState state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return id == game.id &&
                currentPlayerCount == game.currentPlayerCount &&
                tournament == game.tournament &&
                Objects.equal(name, game.name) &&
                state == game.state &&
                Objects.equal(config, game.config);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, id, currentPlayerCount, state, config, tournament);
    }

    @Override
    public String toString() {
        return "Game{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", currentPlayerCount=" + currentPlayerCount +
                ", state=" + state +
                ", config=" + config +
                ", tournament=" + tournament +
                '}';
    }
}
