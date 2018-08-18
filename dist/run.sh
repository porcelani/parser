#!/usr/bin/env bash

java -cp "parser.jar" com.ef.Parser --accesslog=access.log --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100
