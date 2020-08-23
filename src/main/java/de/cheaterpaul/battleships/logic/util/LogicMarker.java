package de.cheaterpaul.battleships.logic.util;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class LogicMarker {
    private LogicMarker() {
    }

    public static final Marker SHOTS = MarkerManager.getMarker("Place Shots");
    public static final Marker SHIPS = MarkerManager.getMarker("Place Ships");
}
