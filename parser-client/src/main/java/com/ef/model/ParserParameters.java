package com.ef.model;

import com.ef.ParameterValidationException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParserParameters {

    private String accesslog;
    private Date startDate;
    private Duration duration;
    private Integer threshold;

    private ParserParameters() {
    }

    public String getAccesslog() {
        return accesslog;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public static final class ParserParametersBuilder {
        private String accesslog;
        private Date startDate;
        private Duration duration;
        private Integer threshold;

        private ParserParametersBuilder() {
        }

        public static ParserParametersBuilder aParserParameters() {
            return new ParserParametersBuilder();
        }

        public ParserParametersBuilder withAccesslog(String accesslog) throws ParameterValidationException {
            if (accesslog == null) {
                throw new ParameterValidationException("\"Parser\" require --accesslog argument.\n");
            }

            this.accesslog = accesslog;
            return this;
        }

        public ParserParametersBuilder withStartDate(String startDate) throws ParameterValidationException {
            if (startDate == null) {
                throw new ParameterValidationException("\"Parser\" require --startDate argument.\n");
            }

            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");
            Date date;
            try {
                date = dt.parse(startDate);
            } catch (ParseException e) {
                throw new ParameterValidationException("\"Parser\" require --startDate yyyy-MM-dd.HH:mm:ss format.\n");
            }
            this.startDate = date;
            return this;
        }

        public ParserParametersBuilder withDuration(String duration) throws ParameterValidationException {
            if (duration == null) {
                throw new ParameterValidationException("\"Parser\" require --duration argument.\n");
            }
            try {
                this.duration = Duration.valueOf(duration);
            } catch (Exception e) {
                throw new ParameterValidationException("\"Parser\" require --duration take only \"hourly\", \"daily\" as inputs.\n");
            }

            return this;
        }

        public ParserParametersBuilder withThreshold(String threshold) throws ParameterValidationException {
            if (threshold == null) {
                throw new ParameterValidationException("\"Parser\" require --threshold argument.\n");
            }
            try {
                this.threshold = Integer.valueOf(threshold);
            } catch (Exception e) {
                throw new ParameterValidationException("\"Parser\" require --threshold take only Integer as inputs.\n");
            }
            return this;
        }

        public ParserParameters build() {
            ParserParameters parserParameters = new ParserParameters();
            parserParameters.duration = this.duration;
            parserParameters.threshold = this.threshold;
            parserParameters.startDate = this.startDate;
            parserParameters.accesslog = this.accesslog;
            return parserParameters;
        }
    }
}
