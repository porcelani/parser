package com.ef.service;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentsSeparated {

    public static final String REGEX = "--(.+)=(.+)";

    public HashMap<String, String> separate(String[] arguments) {
        HashMap<String, String> map = new HashMap<String, String>();

        for (String argument : arguments) {
            extractValues(map, argument);
        }
        return map;
    }

    private void extractValues(HashMap<String, String> map, String argument) {
        String parameter = argument.replaceAll(" ", "");

        final Pattern pattern = Pattern.compile(REGEX);
        final Matcher matcher = pattern.matcher(parameter);

        try {
            matcher.find();
            map.put(matcher.group(1), matcher.group(2));

        } catch (IllegalStateException e) {
            return;
        }
    }
}
