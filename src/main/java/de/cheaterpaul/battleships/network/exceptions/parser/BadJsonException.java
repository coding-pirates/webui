package de.cheaterpaul.battleships.network.exceptions.parser;

import de.cheaterpaul.battleships.logic.ErrorType;

public class BadJsonException extends ParserException {

    public BadJsonException(String message) {
        super(message, ErrorType.BAD_JSON);
    }

}
