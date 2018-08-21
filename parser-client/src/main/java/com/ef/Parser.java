package com.ef;


import com.ef.model.ParameterValidator;

import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;


public class Parser {
    private static final Logger LOGGER = Logger.getLogger(Parser.class.getName());

    public static void main(String[] args) {

        ParameterValidator parameterValidator = new ParameterValidator();
        try {
            parameterValidator.validate(args);
        } catch (ParameterValidationException e) {
            LOGGER.log(SEVERE, e.getMessage());
        }

    }
}
