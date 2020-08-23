package de.cheaterpaul.battleships.logic;

public enum HitType {
    /**
     * shot has failed
     */
    FAIL,
    /**
     * nothing was hit - "water"
     */
    NONE,
    /**
     * one part of the ship was hit - "hit"
     */
    HIT,
    /**
     * all parts of one ship was hit, the whole ship sunk - "sunken"
     */
    SUNK
}
