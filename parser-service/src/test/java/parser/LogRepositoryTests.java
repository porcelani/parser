package parser;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import parser.entity.Log;
import parser.repository.LogRepository;

import java.util.ArrayList;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogRepositoryTests {


    public static final String IP = "192.168.234.82";
    @Autowired
    private LogRepository logRepository;


    @Ignore
    @Test
    public void shouldParserLog() {
        //Prepare

        Collection<Object> allActiveUsersNative = logRepository.findIPsThatModeMoreThanACertainNumberOfRequestsForAGivenTimePeriod();


        assertThat(newArrayList(allActiveUsersNative).size()).isEqualTo(10);
    }

    @Test
    public void shouldFindRequestsMadeByAGivenIP() {
        Log log = new Log();
        log.setIp(IP);
        logRepository.save(log);


        Collection<Log> allActiveUsersNative = logRepository.findRequestsMadeByAGivenIP(IP);


        ArrayList<Log> logs = newArrayList(allActiveUsersNative);
        assertThat(logs.size()).isEqualTo(1);
        Log actual = logs.get(0);
        assertThat(actual.getId()).isNotNull();

    }
}