package de.cheaterpaul.battleships.logic;

import com.google.common.base.Objects;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nonnull;

/**
 * Represents a shot.
 *
 * @author Interdoc committee, Paul Becker
 */
public class Shot {
    /**
     * Is the ID of the client to whom the shot
     * is fired on
     */
    private final int clientId;
    /**
     * the position to which the shot is fired
     */
    @Nonnull
    @SerializedName("position")
    private final Point2D targetField;

    /**
     * Constructor of the class Shot
     * @param clientId id of the clied, to which this shot belongs to
     * @param targetField {@link Point2D}, where the shot was placed
     */
    public Shot(int clientId, @Nonnull Point2D targetField) {
        this.clientId = clientId;
        this.targetField = targetField;
    }

    /**
     * Return Id of the Client, to which this shot belongs to
     * @return {@link #clientId}
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Return Point, which is the target of this shot
     *
     * @return {@link #targetField}
     */
    @Nonnull
    public Point2D getTargetField() {
        return targetField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shot shot = (Shot) o;
        return clientId == shot.clientId &&
                Objects.equal(targetField, shot.targetField);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clientId, targetField);
    }

    @Override
    public String toString() {
        return "Shot{" +
                "clientId=" + clientId +
                ", targetField=" + targetField +
                '}';
    }
}
