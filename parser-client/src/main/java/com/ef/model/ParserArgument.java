package com.ef.model;

import com.ef.ArgumentValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ParserArgument {

    private String accesslog;
    private String startDate;
    private String duration;
    private String threshold;

    private ParserArgument() {
    }

    public String getAccesslog() {
        return accesslog;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getDuration() {
        return duration;
    }

    public String getThreshold() {
        return threshold;
    }

    public static final class ParserParametersBuilder {
        private String accesslog;
        private String startDate;
        private String duration;
        private String threshold;

        private ParserParametersBuilder() {
        }

        public static ParserParametersBuilder aParserParameters() {
            return new ParserParametersBuilder();
        }

        public ParserParametersBuilder withAccesslog(String accesslog) throws ArgumentValidationException {
            if (accesslog == null) {
                throw new ArgumentValidationException("\"Parser\" require --accesslog argument.\n");
            }

            this.accesslog = accesslog;
            return this;
        }

        public ParserParametersBuilder withStartDate(String startDate) throws ArgumentValidationException {
            if (startDate == null) {
                throw new ArgumentValidationException("\"Parser\" require --startDate argument.\n");
            }

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
            try {
                dt.parse(startDate);
                this.startDate = startDate;
            } catch (ParseException e) {
                throw new ArgumentValidationException("\"Parser\" require --startDate yyyy-MM-dd.HH:mm:ss format.\n");
            }
            return this;
        }

        public ParserParametersBuilder withDuration(String duration) throws ArgumentValidationException {
            if (duration == null) {
                throw new ArgumentValidationException("\"Parser\" require --duration argument.\n");
            }
            try {
                Duration.valueOf(duration);
                this.duration = duration;
            } catch (Exception e) {
                throw new ArgumentValidationException("\"Parser\" require --duration take only \"hourly\", \"daily\" as inputs.\n");
            }

            return this;
        }

        public ParserParametersBuilder withThreshold(String threshold) throws ArgumentValidationException {
            if (threshold == null) {
                throw new ArgumentValidationException("\"Parser\" require --threshold argument.\n");
            }
            try {
                Integer.valueOf(threshold);
                this.threshold = threshold;
            } catch (Exception e) {
                throw new ArgumentValidationException("\"Parser\" require --threshold take only Integer as inputs.\n");
            }
            return this;
        }

        public ParserArgument build() {
            ParserArgument parserParameters = new ParserArgument();
            parserParameters.duration = this.duration;
            parserParameters.threshold = this.threshold;
            parserParameters.startDate = this.startDate;
            parserParameters.accesslog = this.accesslog;
            return parserParameters;
        }
    }
}
