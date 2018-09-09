package parser.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class GetMD5ForFile {

    public String getMD5(MultipartFile multipartFile) {

        String md5 = null;

        try {
            md5 = DigestUtils.md5Hex(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return md5;
    }

}