package parser.log;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

public interface LogService {

    void save(MultipartFile file) throws IOException, ParseException;

    Collection<Object> search(String startDate, String duration, String threshold) throws ParseException;

}
