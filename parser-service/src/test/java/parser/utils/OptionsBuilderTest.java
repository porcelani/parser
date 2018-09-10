package parser.utils;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static java.lang.Integer.valueOf;
import static org.junit.Assert.assertEquals;

public class OptionsBuilderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldBuildOptionsWithHourly() throws OptionValidationException {
        ParserOptions parserOptions = new OptionsBuilder()
                .withDuration("hourly")
                .withThreshold(200)
                .build();

        assertEquals(parserOptions.getDurationInHours(), valueOf(1));
        assertEquals(parserOptions.getThreshold(), valueOf(200));
    }


    @Test
    public void shouldBuildOptionsWithDaily() throws OptionValidationException {
        ParserOptions parserOptions = new OptionsBuilder()
                .withDuration("daily")
                .withThreshold(500)
                .build();

        assertEquals(parserOptions.getDurationInHours(), valueOf(24));
        assertEquals(parserOptions.getThreshold(), valueOf(500));
    }

    @Test
    public void shouldNotBuildWithWrongDuration() throws OptionValidationException {
        thrown.expect(OptionValidationException.class);
        thrown.expectMessage(Matchers.is("Duration value doesn't exist"));

        new OptionsBuilder()
                .withDuration("hour")
                .withThreshold(1)
                .build();
    }

    @Test
    public void shouldNotBuildWithWrongThresholdInHourlyOption() throws OptionValidationException {
        thrown.expect(OptionValidationException.class);
        thrown.expectMessage(Matchers.is("200 as hourly limit"));

        new OptionsBuilder()
                .withDuration("hourly")
                .withThreshold(201)
                .build();
    }

    @Test
    public void shouldNotBuildWithWrongThresholdInDailyOption() throws OptionValidationException {
        thrown.expect(OptionValidationException.class);
        thrown.expectMessage(Matchers.is("500 as daily limit"));

        new OptionsBuilder()
                .withDuration("daily")
                .withThreshold(501)
                .build();
    }

}