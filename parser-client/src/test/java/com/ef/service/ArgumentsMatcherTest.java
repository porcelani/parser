package com.ef.service;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class ArgumentsMatcherTest {

    @Test
    public void shouldSeparated() {
        ArgumentsSeparated argumentsMatcher = new ArgumentsSeparated();
        String[] args = {"--accesslog=access.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};


        HashMap<String, String> separatedArguments = argumentsMatcher.separate(args);


        assertEquals(separatedArguments.size(), 4);
        assertEquals(separatedArguments.get("accesslog"), "access.log");
        assertEquals(separatedArguments.get("startDate"), "2017-01-01.13:00:00");
        assertEquals(separatedArguments.get("duration"), "hourly");
        assertEquals(separatedArguments.get("threshold"), "100");
    }
}