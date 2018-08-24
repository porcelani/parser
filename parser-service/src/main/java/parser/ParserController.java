package parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import parser.entity.Log;
import parser.exception.ParserException;
import parser.log.LogRepository;
import parser.log.LogService;
import parser.storage.StorageService;

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
                             @RequestParam("startDate") String startDate,
                             @RequestParam("duration") String duration,
                             @RequestParam("threshold") String threshold) {

        try {
            storageService.store(file);

            logService.save(file);

            return logService.search(startDate, duration, threshold);


        } catch (Exception e) {
            throw new ParserException("Sorry, something wrong happened. Reset the service and try again.");
        }
    }

    @GetMapping(path = "")
    public @ResponseBody
    Iterable<Log> getAllLogs() {
        return logRepository.findAll();
    }
}
