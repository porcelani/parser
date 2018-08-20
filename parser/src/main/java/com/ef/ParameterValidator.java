package com.ef;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterValidator {

    public static final int QUANTITY_OF_ARGUMENTS = 4;


    public ParameterValidator() {

    }

    public void validate(String[] arguments) throws ParameterValidationException {

        if (arguments.length != QUANTITY_OF_ARGUMENTS) {
            throw new ParameterValidationException("\"Parser\" requires exactly 4 arguments.\n");
        }

        HashMap<String, String> argumentMap = parameterMaps(arguments);

        if (!argumentMap.containsKey("accesslog")) {
            throw new ParameterValidationException("\"Parser\" require --accesslog argument.\n");
        }

        if (!argumentMap.containsKey("startDate")) {
            throw new ParameterValidationException("\"Parser\" require --startDate argument.\n");
        }

        if (!argumentMap.containsKey("duration")) {
            throw new ParameterValidationException("\"Parser\" require --duration argument.\n");
        }

        if (!argumentMap.containsKey("threshold")) {
            throw new ParameterValidationException("\"Parser\" require --threshold argument.\n");
        }

    }

    private HashMap<String, String> parameterMaps(String[] arguments) {
        HashMap<String, String> map = new HashMap<>();
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
