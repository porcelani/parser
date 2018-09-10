package parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import parser.entity.Log;
import parser.log.LogRepository;
import parser.log.LogService;
import parser.storage.StorageService;
import parser.utils.OptionValidationException;
import parser.utils.OptionsBuilder;
import parser.utils.ParserOptions;

import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping(path = "/parser")
public class ParserController {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private StorageService storageService;

    @Autowired
    private LogService logService;

    @PostMapping()
    public @ResponseBody
    Iterable<Object> addLogs(@RequestParam("file") MultipartFile file,
                             @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm:ss") Date startDate,
                             @RequestParam("duration") String duration,
                             @RequestParam("threshold") Integer threshold) throws ParseException, OptionValidationException {

        ParserOptions options = new OptionsBuilder()
                .withDuration(duration)
                .withThreshold(threshold)
                .build();

        storageService.save(file);

        return logService.search(startDate, options.getDurationInHours(), options.getThreshold());

    }


    @GetMapping(path = "")
    public @ResponseBody
    Iterable<Log> getAllLogs() {
        return logRepository.findAll();
    }

}
