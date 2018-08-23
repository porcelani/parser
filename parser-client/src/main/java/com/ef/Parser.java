package com.ef;


import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class Parser {

    public static final String URL_STRING = "http://localhost:8080/parser";
    public static final String FILE_PATH = "/home/porcelani/workspace/bitbucket/wallethub-application/dist/access.log";


    public static void main(String args[]) throws Exception {
        post();
    }


    private static void post() {
        try {
            FileSystemResource resource = new FileSystemResource("/home/porcelani/workspace/bitbucket/wallethub-application/dist/access.log");

            MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
            map.add("file", resource);
            map.add("startDate", "2017-01-01.13:00:00");
            map.add("duration", "daily");
            map.add("threshold", "250");


            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(URL_STRING, map, String.class);


            if (response.getStatusCodeValue() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusCode());
            }

            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
