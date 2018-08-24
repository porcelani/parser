package com.ef.service;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentsSeparated {

    public static final String REGEX = "--(.+)=(.+)";

    public HashMap<String, String> separate(String[] arguments) {
        HashMap<String, String> map = new HashMap<String, String>();
        for (String argument : arguments) {
            String parameter = argument.replaceAll(" ", "");

            final Pattern pattern = Pattern.compile(REGEX);
            final Matcher matcher = pattern.matcher(parameter);
            matcher.find();

            map.put(matcher.group(1), matcher.group(2));
        }
        return map;
    }
}
