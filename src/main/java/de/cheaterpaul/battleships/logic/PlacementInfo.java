package de.cheaterpaul.battleships.logic;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;

/**
 * Represents the unique position of a ship.
 *
 * @author Interdoc committee, Paul Becker
 */
public class PlacementInfo {

    /**
     * Is the point in the coordinate system, on
     * the ship is positioned.
     */
    @Nonnull
    private Point2D position;
    /**
     * Is the clockwise rotation of the
     * ship
     */
    @Nonnull
    private Rotation rotation;

    /**
     * Constructor of the class PlacementInfo
     * @param position {@link Point2D} lowest, left point, where the ship should be placed at
     * @param rotation {@link Rotation} how often rotate the ship
     */
    public PlacementInfo(@Nonnull Point2D position, @Nonnull Rotation rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    /**
     * Return the point, where the lowest left of the ship should be placed
     * @return {@link #position}
     */
    @Nonnull
    public Point2D getPosition() {
        return position;
    }

    /**
     * return the rotation, how the ship should be placed in the field
     *
     * @return {@link #rotation}
     */
    @Nonnull
    public Rotation getRotation() {
        return rotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlacementInfo that = (PlacementInfo) o;
        return Objects.equal(position, that.position) &&
                rotation == that.rotation;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position, rotation);
    }

    @Override
    public String toString() {
        return "PlacementInfo{" +
                "position=" + position +
                ", rotation=" + rotation +
                '}';
    }
}
