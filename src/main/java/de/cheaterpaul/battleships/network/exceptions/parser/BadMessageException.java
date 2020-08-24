package de.cheaterpaul.battleships.network.exceptions.parser;

import de.cheaterpaul.battleships.logic.ErrorType;

public class BadMessageException extends ParserException {

    public BadMessageException(final String message) {
        super(message, ErrorType.BAD_MESSAGE);
    }
}
