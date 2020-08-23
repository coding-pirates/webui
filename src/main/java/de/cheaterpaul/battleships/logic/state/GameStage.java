package de.cheaterpaul.battleships.logic.state;

public enum GameStage {
    /**
     * Start of the game
     */
    START,
    /**
     * the player could place their shots
     */
    SHOTS,
    /**
     * the shots are visualized
     */
    VISUALIZATION,
    /**
     * the player place his/her ships on the field
     */
    PLACESHIPS,
    /**
     * the game is over
     */
    FINISHED
}
