package com.ef.model;

import com.ef.ParameterValidationException;
import com.ef.model.ParserParameters.ParserParametersBuilder;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterValidator {

    public static final int QUANTITY_OF_ARGUMENTS = 4;


    public ParserParameters validate(String[] arguments) throws ParameterValidationException {

        if (arguments.length != QUANTITY_OF_ARGUMENTS) {
            throw new ParameterValidationException("\"Parser\" requires exactly 4 arguments.\n");
        }

        HashMap<String, String> argumentMap = parameterMaps(arguments);

        return ParserParametersBuilder.aParserParameters()
                .withAccesslog(argumentMap.get("accesslog"))
                .withStartDate(argumentMap.get("startDate"))
                .withDuration(argumentMap.get("duration"))
                .withThreshold(argumentMap.get("threshold"))
                .build();

    }

    private HashMap<String, String> parameterMaps(String[] arguments) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (String argument : arguments) {
            String parameter = argument.replaceAll(" ", "");

            final Pattern pattern = Pattern.compile("--(.+)=(.+)");
            final Matcher matcher = pattern.matcher(parameter);
            matcher.find();

            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }
}
