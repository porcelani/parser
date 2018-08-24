package com.ef;

public class ArgumentValidationException extends Exception {

    public static final String USAGE_MESSAGE = "\n" +
            "Usage:  java -cp \"parser.jar\" com.ef.Parser --accesslog=[/path/to/file.log] --startDate=[yyyy-MM-dd.HH:mm:ss] --duration=[hourly/daily] --threshold=[integer] \n" +
            "\n" +
            "Required Arguments:\n" +
            "--accesslog            Path to log file\n" +
            "--startDate            Use just \"yyyy-MM-dd.HH:mm:ss\" format\n" +
            "--duration             Can take only \"hourly\", \"daily\" as inputs \n" +
            "--threshold            Can be an integer\n" +
            "";

    public ArgumentValidationException(String msg) {
        super(msg + USAGE_MESSAGE);
    }

}
