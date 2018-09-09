package parser.storage;

import org.springframework.data.repository.CrudRepository;
import parser.entity.FileCache;

import java.util.List;

public interface FileCacheRepository extends CrudRepository<FileCache, Integer> {

    List<FileCache> findByMd5(String md5);

    List<FileCache> findAll();

    void deleteByMd5(String md5);

}
