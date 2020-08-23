package de.cheaterpaul.battleships.logic;

/**
 * @author Interdoc committee, Paul Becker
 */
public enum ErrorType {
    /**
     * Occurs when the client is not authorized to perform a specific action
     * or to ask for information.
     */
    NOT_ALLOWED,
    /**
     * Occurs when a client takes an action at a wrong time or during
     * a game state that does not allow this action to attempt to execute.
     */
    INVALID_ACTION,
    /**
     * Occurs when the submitted JSON string has a syntax error.
     */
    BAD_JSON,
    /**
     * Occurs when the JSON string is syntactically correct, but it is not
     * Message class can be assigned.
     */
    BAD_MESSAGE
}
