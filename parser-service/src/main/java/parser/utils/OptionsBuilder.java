package parser.utils;

public class OptionsBuilder {
    private static final int HOURS_OF_ONE_DAY = 24;
    private static final int HOURS_OF_ONE_HOUR = 1;

    private static final int DAYLY_LIMITE = 500;
    private static final int HOURLY_LIMITE = 200;
    private Integer durationInHours;
    private Integer threshold;

    public OptionsBuilder withDuration(String duration) throws OptionValidationException {
        if ("hourly".equals(duration)) {
            durationInHours = 1;
        }else if ("daily".equals(duration)) {
            durationInHours = 24;
        }else {
            throw new OptionValidationException("Duration value doesn't exist");
        }

        return this;
    }

    public OptionsBuilder withThreshold(int value) throws OptionValidationException {

        if (value > DAYLY_LIMITE && durationInHours == HOURS_OF_ONE_DAY)
            throw new OptionValidationException("500 as daily limit");
        if (value > HOURLY_LIMITE && durationInHours == HOURS_OF_ONE_HOUR)
            throw new OptionValidationException("200 as hourly limit");
        this.threshold = value;

        return this;
    }

    public ParserOptions build() {
        return new ParserOptions(durationInHours, threshold);
    }
}
