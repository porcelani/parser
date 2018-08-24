package com.ef;

import com.ef.model.ArgumentValidator;
import com.ef.model.ParserArgument;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.text.SimpleDateFormat;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;

public class ArgumentValidatorTest {

    private ArgumentValidator argumentConstructor;
    public static final String USAGE_MESSAGE = "\n" +
            "Usage:  java -cp \"parser.jar\" com.ef.Parser --accesslog=[/path/to/file.log] --startDate=[yyyy-MM-dd.HH:mm:ss] --duration=[hourly/daily] --threshold=[integer] \n" +
            "\n" +
            "Required Arguments:\n" +
            "--accesslog            Path to log file\n" +
            "--startDate            Use just \"yyyy-MM-dd.HH:mm:ss\" format\n" +
            "--duration             Can take only \"hourly\", \"daily\" as inputs \n" +
            "--threshold            Can be an integer\n" +
            "";


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private HashMap<String, String> argumentsMap;
    public static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

    @Before
    public void setUp() {
        argumentConstructor = new ArgumentValidator();
        argumentsMap = new HashMap<String, String>();

    }

    @Test
    public void shouldNotThrowWhenUsedCorrectQuantityAndNameOfArguments() throws Exception {
        argumentsMap.put("accesslog", "access.log");
        argumentsMap.put("startDate", "2017-01-01.13:00:00");
        argumentsMap.put("duration", "hourly");
        argumentsMap.put("threshold", "100");


        ParserArgument parserArgument = argumentConstructor.validate(argumentsMap);


        assertEquals(parserArgument.getAccesslog(), "access.log");
        assertEquals(parserArgument.getStartDate(), "2017-01-01.13:00:00");
        assertEquals(parserArgument.getDuration(), "hourly");
        assertEquals(parserArgument.getThreshold(), "100");
    }

    @Test
    public void shouldThrowWhenUsedIncorrectQuantityOfArguments() throws Exception {
        thrown.expect(ArgumentValidationException.class);
        thrown.expectMessage(is("\"Parser\" requires exactly 4 arguments.\n" + USAGE_MESSAGE));

        argumentsMap.put("accesslog", "access.log");
        argumentsMap.put("startDate", "2017-01-01.13:00:00");
        argumentsMap.put("duration", "hourly");

        argumentConstructor.validate(argumentsMap);
    }

    @Test
    public void shouldThrowWhenForgetAccesslogArguments() throws Exception {
        thrown.expect(ArgumentValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --accesslog argument.\n" + USAGE_MESSAGE));

        argumentsMap.put("access", "access.log");
        argumentsMap.put("startDate", "2017-01-01.13:00:00");
        argumentsMap.put("duration", "hourly");
        argumentsMap.put("threshold", "100");


        argumentConstructor.validate(argumentsMap);
    }

    @Test
    public void shouldThrowWhenForgetStartDateArguments() throws Exception {
        thrown.expect(ArgumentValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --startDate argument.\n" + USAGE_MESSAGE));

        argumentsMap.put("accesslog", "access.log");
        argumentsMap.put("start", "2017-01-01.13:00:00");
        argumentsMap.put("duration", "hourly");
        argumentsMap.put("threshold", "100");


        argumentConstructor.validate(argumentsMap);
    }

    @Test
    public void shouldThrowWhenUseWrongStartDateFormat() throws Exception {
        thrown.expect(ArgumentValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --startDate yyyy-MM-dd.HH:mm:ss format.\n" + USAGE_MESSAGE));

        argumentsMap.put("accesslog", "access.log");
        argumentsMap.put("startDate", "2017-01-01");
        argumentsMap.put("duration", "hourly");
        argumentsMap.put("threshold", "100");


        argumentConstructor.validate(argumentsMap);
    }


    @Test
    public void shouldThrowWhenForgetDurationArguments() throws Exception {
        thrown.expect(ArgumentValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --duration argument.\n" + USAGE_MESSAGE));

        argumentsMap.put("accesslog", "access.log");
        argumentsMap.put("startDate", "2017-01-01.13:00:00");
        argumentsMap.put("dur", "hourly");
        argumentsMap.put("threshold", "100");


        argumentConstructor.validate(argumentsMap);
    }

    @Test
    public void shouldThrowWhenWrongDurationValue() throws Exception {
        thrown.expect(ArgumentValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --duration take only \"hourly\", \"daily\" as inputs.\n" + USAGE_MESSAGE));

        argumentsMap.put("accesslog", "access.log");
        argumentsMap.put("startDate", "2017-01-01.13:00:00");
        argumentsMap.put("duration", "hour");
        argumentsMap.put("threshold", "100");


        argumentConstructor.validate(argumentsMap);
    }


    @Test
    public void shouldThrowWhenForgetThresholdArguments() throws Exception {
        thrown.expect(ArgumentValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --threshold argument.\n" + USAGE_MESSAGE));

        argumentsMap.put("accesslog", "access.log");
        argumentsMap.put("startDate", "2017-01-01.13:00:00");
        argumentsMap.put("duration", "hourly");
        argumentsMap.put("thre", "100");


        argumentConstructor.validate(argumentsMap);
    }

    @Test
    public void shouldThrowWhenWrongThresholdValue() throws Exception {
        thrown.expect(ArgumentValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --threshold take only Integer as inputs.\n" + USAGE_MESSAGE));

        argumentsMap.put("accesslog", "access.log");
        argumentsMap.put("startDate", "2017-01-01.13:00:00");
        argumentsMap.put("duration", "hourly");
        argumentsMap.put("threshold", "a100");


        argumentConstructor.validate(argumentsMap);
    }

}