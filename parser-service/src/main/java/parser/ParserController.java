package parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import parser.entity.FileCache;
import parser.entity.Log;
import parser.exception.ParserException;
import parser.log.LogRepository;
import parser.log.LogService;
import parser.storage.FileCacheRepository;
import parser.storage.StorageService;
import parser.utils.GetMD5ForFile;

import java.text.ParseException;
import java.util.Date;

@Controller
@RequestMapping(path = "/parser")
public class ParserController {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private FileCacheRepository fileCacheRepository;

    @Autowired
    private LogService logService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private GetMD5ForFile getMD5ForFile;

    @PostMapping()
    public @ResponseBody
    Iterable<Object> addLogs(@RequestParam("file") MultipartFile file,
                             @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd.HH:mm:ss") Date startDate,
                             @RequestParam("duration") String duration,
                             @RequestParam("threshold") Integer threshold) throws ParseException {
        String md5 = "";
        try {

            md5 = getMD5ForFile.getMD5(file);
            if (fileCacheRepository.findByMd5(md5).isEmpty()) {
                storageService.store(file);
                logService.save(file,md5);
                fileCacheRepository.save(new FileCache(md5));
            }
        } catch (Exception e) {
            logRepository.deleteByMd5(md5);
            fileCacheRepository.deleteByMd5(md5);
            throw new ParserException("Sorry, something wrong happened. Reset the service and try again.");
        }

        return logService.search(startDate, getDurationInHours(duration), threshold);

    }

    @GetMapping(path = "")
    public @ResponseBody
    Iterable<Log> getAllLogs() {
        return logRepository.findAll();
    }


    private int getDurationInHours(@RequestParam("duration") String duration) {
        return "daily".equals(duration) ? 24 : 1;
    }
}
