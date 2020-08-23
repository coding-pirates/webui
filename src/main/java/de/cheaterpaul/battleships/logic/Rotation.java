package de.cheaterpaul.battleships.logic;

import com.google.gson.annotations.SerializedName;

/**
 * @author Paul Becker
 */
public enum Rotation {
    /**
     * ship should not be turned in any direction
     */
    @SerializedName("0") NONE,
    /**
     * ship should be turned clockwise with 90°
     */
    @SerializedName("1") CLOCKWISE_90,
    /**
     * ship should be turned clockwise with 180°
     */
    @SerializedName("2") CLOCKWISE_180,
    /**
     * ship should be turned counterclockwise with 90°
     */
    @SerializedName("3") COUNTERCLOCKWISE_90

}
