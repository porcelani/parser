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

@Controller
@RequestMapping(path = "/parser")
public class ParserController {

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private StorageService storageService;

    @PostMapping("/")
    public @ResponseBody
    String addLogs(@RequestParam("file") MultipartFile file,
                   @RequestParam("startDate") String startDate,
                   @RequestParam("duration") String duration,
                   @RequestParam("threshold") String threshold) throws IOException {

        storageService.store(file);
        InputStream inputStream = file.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            Log n = new Log();
            n.setAgent(line);
            logRepository.save(n);
        }


        return "Saved";
    }

    @GetMapping(path = "/")
    public @ResponseBody
    Iterable<Log> getAllLogs() {
        return logRepository.findAll();
    }
}
