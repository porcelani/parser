package parser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import parser.repository.LogRepository;
import parser.storage.StorageService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParserIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private LogRepository logRepository;

    @MockBean
    private StorageService storageService;

    @LocalServerPort
    private int port;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldParserLog() throws Exception {
        ClassPathResource resource = new ClassPathResource("access.log", getClass());
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        map.add("file", resource);
        map.add("startDate", "");
        map.add("duration", "");
        map.add("threshold", "");


        ResponseEntity<String> response = this.restTemplate.postForEntity("/parser/", map, String.class);


        assertThat(response.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
        then(storageService).should().store(any(MultipartFile.class));
        assertThat(newArrayList(logRepository.findAll()).size()).isEqualTo(10);


    }
}