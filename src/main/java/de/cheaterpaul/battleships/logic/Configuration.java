package de.cheaterpaul.battleships.logic;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import javax.annotation.Nonnull;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Represents the configuration for a game.
 *
 * @author Interdoc committee
 * @author Paul Becker
 */
public class Configuration {


    /**
     * max player possible in one game
     */
    public final int maxPlayerCount;

    /**
     * Specifies the height of the playing field
     */
    public final int height;

    /**
     * Specifies the width of the playing field
     */
    public final int width;

    /**
     * Gives the number of possible shots per Round on
     */
    public final int shotCount;

    /**
     * Indicates the number of points a hit gives
     */
    public final int hitPoints;

    /**
     * Indicates the number of points a sunken ship gives
     */
    public final int sunkPoints;

    /**
     * time to place the shots
     */
    public final long roundTime;

    /**
     * Sets the time for animations to run.
     * before the timer for
     * the roundTime starts
     */
    public final long visualizationTime;

    /**
     * Sets the points to subtract
     * if an invalid move is made
     */
    public final int penaltyMinusPoints;

    /**
     * Sets the type of penalty for an invalid
     * Play
     */
    public final PenaltyType penaltyKind;

    /**
     * A map that maps from unique ID
     * to a shipType
     */
    public final ImmutableMap<Integer, ShipType> ships;

    public static final Configuration DEFAULT = new Builder().build();

    /**
     * Constructor of the class Configuration
     *
     * @param maxPlayerCount max amount od players
     * @param height height of the field
     * @param width width of the field
     * @param shotCount amount of shots per move
     * @param hitPoints amount of points for hitting the ship
     * @param sunkPoints amount of points for sinking a ship
     * @param roundTime duration for one move
     * @param visualizationTime duration for visualize the move
     * @param shipTypes types of the ships
     * @param penaltyMinusPoints amount of reducing points for penalty
     * @param penaltyType type of penalty
     */
    public Configuration(int maxPlayerCount, int height, int width, int shotCount, int hitPoints, int sunkPoints, long roundTime, long visualizationTime, @Nonnull Map<Integer, ShipType> shipTypes, int penaltyMinusPoints, PenaltyType penaltyType) {
        this.maxPlayerCount = maxPlayerCount;
        this.height = height;
        this.width = width;
        this.shotCount = shotCount;
        this.hitPoints = hitPoints;
        this.sunkPoints = sunkPoints;
        this.roundTime = roundTime;
        this.visualizationTime = visualizationTime;
        this.ships = ImmutableMap.copyOf(shipTypes);
        this.penaltyMinusPoints = penaltyMinusPoints;
        this.penaltyKind = penaltyType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return maxPlayerCount == that.maxPlayerCount &&
                height == that.height &&
                width == that.width &&
                shotCount == that.shotCount &&
                hitPoints == that.hitPoints &&
                sunkPoints == that.sunkPoints &&
                roundTime == that.roundTime &&
                visualizationTime == that.visualizationTime &&
                penaltyMinusPoints == that.penaltyMinusPoints &&
                penaltyKind == that.penaltyKind &&
                Objects.equal(ships, that.ships);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maxPlayerCount, height, width, shotCount, hitPoints, sunkPoints, roundTime, visualizationTime, penaltyMinusPoints, penaltyKind, ships);
    }

    public static final class Builder {

        private int maxPlayerCount = 4;

        private int height = 10;

        private int width = 10;

        private int shotCount = 4;

        private int hitPoints = 1;

        private int sunkPoints = 1;

        private long roundTime = 10_000;

        private long visualizationTime = 1_000;

        private int penaltyMinusPoints = 1;

        private PenaltyType penaltyKind = PenaltyType.POINTLOSS;

        private Map<Integer, ShipType> ships;

        public Builder() {
            ships = new IdentityHashMap<>();
            ships.put(0, new ShipType(
                new Point2D(3, 3),
                new Point2D(4, 3),
                new Point2D(3, 4)
            ));
        }

        @Nonnull
        public Configuration build() {
            return new Configuration(
                maxPlayerCount,
                height,
                width,
                shotCount,
                hitPoints,
                sunkPoints,
                roundTime,
                visualizationTime,
                ships,
                penaltyMinusPoints,
                penaltyKind
            );
        }

        public Builder maxPlayerCount(final int maxPlayerCount) {
            this.maxPlayerCount = maxPlayerCount;
            return this;
        }

        public Builder height(final int height) {
            this.height = height;
            return this;
        }

        public Builder width(final int width) {
            this.width = width;
            return this;
        }

        public Builder shotCount(final int shotCount) {
            this.shotCount = shotCount;
            return this;
        }

        public Builder hitPoints(final int hitPoints) {
            this.hitPoints = hitPoints;
            return this;
        }

        public Builder sunkPoints(final int sunkPoints) {
            this.sunkPoints = sunkPoints;
            return this;
        }

        public Builder roundTime(final long roundTime) {
            this.roundTime = roundTime;
            return this;
        }

        public Builder visualizationTime(final long visualizationTime) {
            this.visualizationTime = visualizationTime;
            return this;
        }

        public Builder penaltyMinusPoints(final int penaltyMinusPoints) {
            this.penaltyMinusPoints = penaltyMinusPoints;
            return this;
        }

        public Builder penaltyKind(@Nonnull final PenaltyType penaltyKind) {
            this.penaltyKind = penaltyKind;
            return this;
        }

        public Builder ships(@Nonnull final Map<Integer, ShipType> ships) {
            this.ships = ships;
            return this;
        }
    }
}