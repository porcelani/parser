package parser.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import parser.entity.Log;

import java.util.Collection;
import java.util.Date;

public interface LogRepository extends CrudRepository<Log, Integer> {

    @Query(nativeQuery = true)
    Collection<Object> findIPsThatModeMoreThanACertainNumberOfRequestsForAGivenTimePeriod(
            @Param("searchDateFrom") Date searchDateFrom,
            @Param("searchDateTo") Date searchDateTo,
            @Param("searchThreshold") Integer threshold);

    @Query(nativeQuery = true)
    Collection<Log> findRequestsMadeByAGivenIP(@Param("searchIP") String searchIP);

}
