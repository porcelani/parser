package parser;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import parser.entity.Log;
import parser.log.LogRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogRepositoryTests {


    public static final String IP_1 = "192.168.101.1";
    public static final String IP_2 = "192.168.101.2";
    @Autowired
    private LogRepository logRepository;


    @Before
    public void setUp() {
        logRepository.deleteAll();

        Log logOfToday = new Log();
        logOfToday.setIp(IP_1);
        logOfToday.setDate(new Date());
        logRepository.save(logOfToday);

        Log log2OfToday = new Log();
        log2OfToday.setIp(IP_1);
        log2OfToday.setDate(new Date());
        logRepository.save(log2OfToday);


        DateTime dateTimeOfYesterday = new DateTime();
        dateTimeOfYesterday = dateTimeOfYesterday.minusDays(1);
        Log logOfyesterday = new Log();
        logOfyesterday.setIp(IP_2);
        logOfyesterday.setDate(dateTimeOfYesterday.toDate());
        logRepository.save(logOfyesterday);
    }

    @Test
    public void shouldParserLog() {
        DateTime dateTime = new DateTime();
        dateTime = dateTime.minusHours(1);
        Date startDate = dateTime.toDate();
        dateTime = dateTime.plusHours(1);
        Date endDate = dateTime.toDate();


        Collection<Object> allActiveUsersNative = logRepository.findIPsThatModeMoreThanACertainNumberOfRequestsForAGivenTimePeriod(startDate, endDate,2);


        assertThat(newArrayList(allActiveUsersNative).size()).isEqualTo(1);
    }

    @Test
    public void shouldFindRequestsMadeByAGivenIP() {

        Collection<Log> allActiveUsersNative = logRepository.findRequestsMadeByAGivenIP(IP_2);


        ArrayList<Log> logs = newArrayList(allActiveUsersNative);
        assertThat(logs.size()).isEqualTo(1);
        Log actual = logs.get(0);
        assertThat(actual.getId()).isNotNull();

    }
}