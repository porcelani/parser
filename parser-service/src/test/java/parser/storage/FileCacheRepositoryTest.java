package parser.storage;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import parser.entity.FileCache;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FileCacheRepositoryTest {
    public static final String MD_5 = "123456";

    @Autowired
    private FileCacheRepository fileCacheRepository;

    @Before
    public void setUp() {
        fileCacheRepository.deleteAll();

        FileCache fileCache = new FileCache(MD_5);
        fileCacheRepository.save(fileCache);
    }

    @Test
    public void shouldFindMd5() {
        List<FileCache> list = fileCacheRepository.findByMd5(MD_5);

        assertEquals(list.size(), 1);
    }

    @Transactional
    @Test
    public void shouldDeletedMd5() {
        fileCacheRepository.deleteByMd5(MD_5);

        List<FileCache> all = fileCacheRepository.findAll();

        assertTrue(all.isEmpty());
    }


}