package de.cheaterpaul.battleships.logic.util;

import de.cheaterpaul.battleships.logic.Point2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Helper Class for rotating ships and making all their coordinates positive
 * Uses a rotation matrix: rotating the ship object around the zero point.
 * <p>
 * Used for getting all possible rotations of one ship.
 *
 * @author Leonie Lender, Lukas Kröger
 */
public class Rotator {
    private static final Logger logger = LogManager.getLogger();


    /**
     * Constructor for {@link Rotator}.
     *
     */
    public Rotator() {

    }

    /**
     * Creates a collection of collections of all possible ship rotations
     *
     * @param positions Collection of points which represents a ship
     * @param rotationEnum The Rotation
     * @return turned ArrayList of rotated points
     */
    public ArrayList<Point2D> rotateShipsTest(Collection<Point2D> positions, int rotationEnum) {
        ArrayList<Point2D> turned = new ArrayList<>();
        //rotation Matix
        for (Point2D point : positions) {
            double rotation = (4 - rotationEnum) * Math.PI / 2;
            int x = (int) Math.round(point.getX() * Math.cos(rotation) - point.getY() * Math.sin(rotation));
            int y = (int) Math.round(point.getX() * Math.sin(rotation) + point.getY() * Math.cos(rotation));
            turned.add(new Point2D(x,y));
        }
        return turned;
    }

}
