package com.ef;


import com.ef.model.ArgumentValidator;
import com.ef.model.ParserArgument;
import com.ef.service.ArgumentsSeparated;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;


public class Parser {

    private static final String URL_STRING = "http://localhost:8080/parser";


    public static void main(String args[]) {
        ArgumentValidator argumentValidator = new ArgumentValidator();
        ArgumentsSeparated argumentsSeparated = new ArgumentsSeparated();

        try {
            HashMap<String, String> argumentsMap = argumentsSeparated.separate(args);
            ParserArgument parserArgument = argumentValidator.validate(argumentsMap);
            post(parserArgument);
        } catch (ArgumentValidationException e) {
            out.println(e.getMessage());
        } catch (Exception e) {
            out.println(e.getMessage());
        }
    }


    private static void post(ParserArgument arguments) {
        FileSystemResource resource = new FileSystemResource(arguments.getAccessLog());

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("file", resource);
        map.add("startDate", arguments.getStartDate());
        map.add("duration", arguments.getDuration());
        map.add("threshold", arguments.getThreshold());


        ResponseEntity response = postCall(map);

        if (response.getStatusCodeValue() != 200) {
            throw new RuntimeException("Failed : error code : " + response.getStatusCode());
        }

        out.println(response.getBody());

    }

    private static ResponseEntity postCall(Map map){

        Runnable runnable = () -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                out.println("Sending a file for the first time to be reviewed may take a few seconds. Wait.\n");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(URL_STRING, map, String.class);

        if(thread.isAlive()){
            thread.stop();
        }

        return response;
    }
}
