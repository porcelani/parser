package parser.utils;

public class ParserOptions {


    private final Integer durationInHours;
    private final Integer threshold;

    public ParserOptions(Integer durationInHours, Integer threshold) {
        this.durationInHours = durationInHours;
        this.threshold=threshold;
    }

    public Integer getDurationInHours() {
        return durationInHours;
    }

    public Integer getThreshold() {
        return threshold;
    }
}
