package parser;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import parser.repository.LogRepository;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogRepositoryTests {


    @Autowired
    private LogRepository logRepository;


    @Ignore
    @Test
    public void shouldParserLog() throws Exception {
        //Prepare

        Collection<Object> allActiveUsersNative = logRepository.findIPsThatModeMoreThanACertainNumberOfRequestsForAGivenTimePeriod();


        assertThat(newArrayList(allActiveUsersNative).size()).isEqualTo(10);
    }
}