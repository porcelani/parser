package parser.log;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

public interface LogService {

    void save(MultipartFile file) throws IOException, ParseException;

    Collection<Object> search(Date startDate, Integer durationInHours, Integer threshold) throws ParseException;
}
