package parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import parser.entity.Log;
import parser.exception.ParserException;
import parser.log.LogRepository;
import parser.log.LogService;
import parser.storage.StorageService;

import java.util.Date;

@Controller
@RequestMapping(path = "/parser")
public class ParserController {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private StorageService storageService;

    @PostMapping()
    public @ResponseBody
    Iterable<Object> addLogs(@RequestParam("file") MultipartFile file,
                             @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm:ss") Date startDate,
                             @RequestParam("duration") String duration,
                             @RequestParam("threshold") Integer threshold) {

        try {
            storageService.store(file);

            logService.save(file);

            return logService.search(startDate, getDurationInHours(duration), threshold);


        } catch (Exception e) {
            throw new ParserException("Sorry, something wrong happened. Reset the service and try again.");
        }
    }

    private int getDurationInHours(@RequestParam("duration") String duration) {
        return "daily".equals(duration) ? 24: 1;
    }

    @GetMapping(path = "")
    public @ResponseBody
    Iterable<Log> getAllLogs() {
        return logRepository.findAll();
    }
}
