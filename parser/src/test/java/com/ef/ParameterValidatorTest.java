package com.ef;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;

public class ParameterValidatorTest {

    private ParameterValidator parameterValidator;
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

    @Before
    public void setUp() {
        parameterValidator = new ParameterValidator();
    }

    @Test
    public void shouldNotThrowWhenUsedCorrectQuantityAndNameOfArguments() throws Exception {
        String[] args = {"--accesslog=access.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};

        parameterValidator.validate(args);
    }

    @Test
    public void shouldThrowWhenUsedIncorrectQuantityOfArguments() throws Exception {
        thrown.expect(ParameterValidationException.class);
        thrown.expectMessage(is("\"Parser\" requires exactly 4 arguments.\n" + USAGE_MESSAGE));

        String[] args = {"--accesslog=access.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly"};

        parameterValidator.validate(args);
    }

    @Test
    public void shouldThrowWhenForgetAccesslogArguments() throws Exception {
        thrown.expect(ParameterValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --accesslog argument.\n" + USAGE_MESSAGE));

        String[] args = {"--acceslog=access.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};

        parameterValidator.validate(args);
    }
}