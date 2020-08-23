package de.cheaterpaul.battleships.logic;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;

/**
 * Represents a ship.
 *
 * @author Interdoc committee
 * @author Paul Becker
 * @author Andre Blanke
 */
public class ShipType {

    /**
     * A collection showing the relative positions
     * of the individual parts of the ship
     */
    @Nonnull
    private final Collection<Point2D> positions;

    public ShipType(@Nonnull final Point2D... positions) {
        this(Arrays.asList(positions));
    }

    /**
     * Constructor of the class ShipType
     * @param positions collection over all parts of the ship({@link Point2D})
     */
    public ShipType(@Nonnull final Collection<Point2D> positions) {
        this.positions = positions;
    }

    /**
     * Return all positions of the ship
     *
     * @return {@link Collection<Point2D>}
     */
    @Nonnull
    public Collection<Point2D> getPositions() {
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShipType shipType = (ShipType) o;
        return Objects.equal(positions, shipType.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(positions);
    }

    @Override
    public String toString() {
        return "ShipType{" +
                "positions=" + positions +
                '}';
    }
}
