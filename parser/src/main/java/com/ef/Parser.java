package com.ef;


import static java.lang.System.out;

public class Parser {

    public Parser() {
    }

    public static void main(String[] args) {

        ParameterValidator parameterValidator = new ParameterValidator();
        try {
            parameterValidator.validate(args);
        } catch (ParameterValidationException e) {
            out.println(e.getMessage());
        }
    }
}
