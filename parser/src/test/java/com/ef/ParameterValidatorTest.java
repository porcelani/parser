package com.ef;

import com.ef.model.ParameterValidator;
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

    @Test
    public void shouldThrowWhenForgetStartDateArguments() throws Exception {
        thrown.expect(ParameterValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --startDate argument.\n" + USAGE_MESSAGE));

        String[] args = {"--accesslog=access.log", "--start=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};

        parameterValidator.validate(args);
    }

    @Test
    public void shouldThrowWhenUseWrongStartDateFormat() throws Exception {
        thrown.expect(ParameterValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --startDate yyyy-MM-dd.HH:mm:ss format.\n" + USAGE_MESSAGE));

        String[] args = {"--accesslog=access.log", "--startDate=2017-01-01", "--duration=hourly", "--thre=100"};

        parameterValidator.validate(args);
    }


    @Test
    public void shouldThrowWhenForgetDurationArguments() throws Exception {
        thrown.expect(ParameterValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --duration argument.\n" + USAGE_MESSAGE));

        String[] args = {"--accesslog=access.log", "--startDate=2017-01-01.13:00:00", "--dur=hourly", "--threshold=100"};

        parameterValidator.validate(args);
    }

    @Test
    public void shouldThrowWhenWrongDurationValue() throws Exception {
        thrown.expect(ParameterValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --duration take only \"hourly\", \"daily\" as inputs.\n" + USAGE_MESSAGE));

        String[] args = {"--accesslog=access.log", "--startDate=2017-01-01.13:00:00", "--duration=hour", "--threshold=100"};

        parameterValidator.validate(args);
    }


    @Test
    public void shouldThrowWhenForgetThresholdArguments() throws Exception {
        thrown.expect(ParameterValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --threshold argument.\n" + USAGE_MESSAGE));

        String[] args = {"--accesslog=access.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--thre=100"};

        parameterValidator.validate(args);
    }

    @Test
    public void shouldThrowWhenWrongThresholdValue() throws Exception {
        thrown.expect(ParameterValidationException.class);
        thrown.expectMessage(is("\"Parser\" require --threshold take only Integer as inputs.\n" + USAGE_MESSAGE));

        String[] args = {"--accesslog=access.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=a123"};

        parameterValidator.validate(args);
    }

    @Test
    public void shouldNotThrowWhenUsedCorrectArgumentsInDifferentOrder() throws Exception {
        String[] args = {"--threshold=100", "--duration=hourly", "--startDate=2017-01-01.13:00:00", "--accesslog=access.log"};

        parameterValidator.validate(args);
    }

}