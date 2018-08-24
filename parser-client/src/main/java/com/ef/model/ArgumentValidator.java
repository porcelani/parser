package com.ef.model;

import com.ef.ArgumentValidationException;
import com.ef.model.ParserArgument.ParserParametersBuilder;

import java.util.HashMap;

public class ArgumentValidator {

    public static final int QUANTITY_OF_ARGUMENTS = 4;

    public ParserArgument validate(HashMap<String, String> arguments) throws ArgumentValidationException {

        if (arguments.size() != QUANTITY_OF_ARGUMENTS) {
            throw new ArgumentValidationException("\"Parser\" requires exactly 4 arguments.\n");
        }

        return ParserParametersBuilder.aParserParameters()
                .withAccesslog(arguments.get("accesslog"))
                .withStartDate(arguments.get("startDate"))
                .withDuration(arguments.get("duration"))
                .withThreshold(arguments.get("threshold"))
                .build();
    }

}
