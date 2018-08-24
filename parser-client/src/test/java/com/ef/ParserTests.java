package com.ef;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ParserTests {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8080);


    @Test
    public void shouldRunParser() {
        wireMockRule.stubFor(post(urlEqualTo("/parser"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("{ \"result\": [{\"ip\": \"192.168.101.1\",\"requests\":200\"}]}")));
        String[] args = {"--accesslog=../resources/access.log", "--startDate=2017-01-01.13:00:00", "--duration=hourly", "--threshold=100"};


        Parser.main(args);


        verify(postRequestedFor(urlMatching("/parser")));
    }
}
