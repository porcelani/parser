package com.ef;

public class ParameterValidator {

    public static final int QUANTITY_OF_ARGUMENTS = 4;


    public ParameterValidator() {

    }

    public void validate(String[] arg) throws ParameterValidationException {

        if (arg.length != QUANTITY_OF_ARGUMENTS) {
            throw new ParameterValidationException("\"Parser\" requires exactly 4 arguments.\n");
        }

        if (!arg[0].contains("accesslog")) {
            throw new ParameterValidationException("\"Parser\" require --accesslog argument.\n");
        }

    }
}
