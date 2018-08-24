package parser.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import parser.entity.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static java.lang.Integer.valueOf;
import static parser.entity.Log.DATE_FORMAT_PARTTERN;

@Service
public class LogParserService implements LogService {

    @Autowired
    private LogRepository logRepository;

    @Override
    public void save(MultipartFile file) throws IOException, ParseException {
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


        String line;
        List<Log> list = new ArrayList<>();
        int count = 0;


        while ((line = bufferedReader.readLine()) != null) {
            count++;

            String[] split = line.split("\\|");
            Log newLog = new Log();
            newLog.setDate(new SimpleDateFormat(DATE_FORMAT_PARTTERN).parse(split[0]));
            newLog.setIp(split[1]);
            newLog.setRequest(split[2]);
            newLog.setStatus(valueOf(split[3]));
            newLog.setAgent(split[4]);
            list.add(newLog);

            if (count % 1000 == 0) {
                logRepository.saveAll(list);
                list = new ArrayList<>();
            }
        }
        logRepository.saveAll(list);

    }

    @Override
    public Collection<Object> search(String startDate, String duration, String threshold) throws ParseException {

        //TODO
        //convert Date
        //calculate dateTo
        SimpleDateFormat formatter = new SimpleDateFormat(Log.DATE_FORMAT_PARTTERN);
        Date dateFrom = formatter.parse("2017-01-01 13:00:00.000");
        Date dateTo = formatter.parse("2017-01-01 14:00:00.000");


        return logRepository.findIPsThatModeMoreThanACertainNumberOfRequestsForAGivenTimePeriod(dateFrom, dateTo, Integer.valueOf(threshold));
    }
}