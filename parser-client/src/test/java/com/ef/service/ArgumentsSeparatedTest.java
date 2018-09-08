package com.ef.service;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ArgumentsSeparatedTest {

    @Test
    public void shouldNotFindArguments() {
        ArgumentsSeparated argumentsSeparated = new ArgumentsSeparated();
        String[] strings = {"", "--accesslog access.log", "--accesslog = "};


        HashMap<String, String> arguments = argumentsSeparated.separate(strings);


        assertTrue(arguments.isEmpty());
    }

    @Test
    public void shouldFindArguments() {
        ArgumentsSeparated argumentsSeparated = new ArgumentsSeparated();
        String[] strings = {"", "--accesslog=access.log", "--duration = hourly "};


        HashMap<String, String> arguments = argumentsSeparated.separate(strings);


        assertEquals(arguments.size(), 2);
        assertEquals(arguments.get("accesslog"), "access.log");
        assertEquals(arguments.get("duration"), "hourly");
    }
}