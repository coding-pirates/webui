package de.cheaterpaul.battleships.logic;

import com.google.common.base.Objects;

/**
 * Represents a point in a two-dimensional coordinate system
 *
 * @author Interdoc committee, Paul Becker
 */
public class Point2D {

    /**
     * x-coordinate of the point
     */
    private int x;
    /**
     * y-coordinate of the point
     */
    private int y;

    /**
     * Constructor of the class Point2D
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     */
    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Return the x-coordinate of the point
     * @return {@link #x}
     */
    public int getX() {
        return x;
    }

    /**
     * Return the y-coordinate of the point
     * @return {@link #y}
     */
    public int getY() {
        return y;
    }

    /**
     * Set new point with coordinates of the old point and differences for x and y coordinates
     * @param x difference to new x-coordinate
     * @param y difference to new y-coordinate
     * @return new {@link Point2D}
     */
    public Point2D getPointWithOffset(int x, int y) {
        return new Point2D(this.x + x, this.y + y);
    }

    /**
     * Set new point with coordinates of the old point and add coordinates of a given new point
     * @param point new point, which coordinates are added to the coordinates of the old point
     * @return new {@link Point2D}
     */
    public Point2D getPointWithOffset(Point2D point) {
        return new Point2D(this.x + point.x, this.y + point.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2D point2D = (Point2D) o;
        return x == point2D.x &&
                y == point2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }

    @Override
    public String toString() {
        return "Point2D{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
