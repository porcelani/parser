package com.ef;

import org.junit.Test;

public class ParserTests {

    @Test
    public void shouldRunParser() {
        String[] args = {"--accesslog=/path/to/file", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};
        Parser.main(args);
    }
}
