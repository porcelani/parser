package parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import parser.entity.Log;
import parser.repository.LogRepository;
import parser.storage.StorageService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.valueOf;
import static parser.entity.Log.DATE_FORMAT_PARTTERN;

@Controller
@RequestMapping(path = "/parser")
public class ParserController {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private StorageService storageService;

    @PostMapping()
    public @ResponseBody
    String addLogs(@RequestParam("file") MultipartFile file,
                   @RequestParam("startDate") String startDate,
                   @RequestParam("duration") String duration,
                   @RequestParam("threshold") String threshold) throws IOException {


        storageService.store(file);
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));


        String line;
        List<Log> list = new ArrayList<>();
        int count = 0;
        try {

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
        } catch (ParseException e) {
            return "Error";
        }

        return "Saved";
    }

    @GetMapping(path = "")
    public @ResponseBody
    Iterable<Log> getAllLogs() {
        return logRepository.findAll();
    }
}
