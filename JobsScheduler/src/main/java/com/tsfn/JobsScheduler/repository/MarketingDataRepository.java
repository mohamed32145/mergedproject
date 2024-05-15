package com.tsfn.JobsScheduler.repository;

import com.tsfn.JobsScheduler.model.MarketingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MarketingDataRepository extends JpaRepository<MarketingData, Long> {

    //              SELECT SUM(impressions) FROM loaderdb.marketing_data WHERE TIMESTAMPDIFF(HOUR, STR_TO_DATE(time_stamp, '%Y%m%dT%H:%i'), NOW()) > 12
    @Query(value = "SELECT SUM(m.impressions) FROM loaderdb.marketing_data m WHERE TIMESTAMPDIFF(HOUR, STR_TO_DATE(time_stamp, '%Y%m%dT%H:%i'), NOW()) < :hours and account=:Account", nativeQuery = true)
    Integer getSumOfimpressionsValues( @Param("hours") int hours,@Param("Account") String account);
    @Query(value = "SELECT SUM(views) FROM loaderdb.marketing_data WHERE TIMESTAMPDIFF(HOUR, STR_TO_DATE(time_stamp, '%Y%m%dT%H:%i'), NOW()) < :hours and account=:Account", nativeQuery = true)
    Integer getSumOfviewsValues( @Param("hours") int hours,@Param("Account") String account);
    @Query(value = "SELECT SUM(clicks) FROM loaderdb.marketing_data WHERE TIMESTAMPDIFF(HOUR, STR_TO_DATE(time_stamp, '%Y%m%dT%H:%i'), NOW()) < :hours and account=:Account", nativeQuery = true)
    Integer getSumOfclicksValues( @Param("hours") int hours,@Param("Account") String account);
    @Query(value = "SELECT SUM(m.likes) FROM loaderdb.marketing_data m WHERE TIMESTAMPDIFF(HOUR, STR_TO_DATE(time_stamp, '%Y%m%dT%H:%i'), NOW()) < :hours and account=:Account", nativeQuery = true)
    Integer getSumOflikesValues( @Param("hours") int hours,@Param("Account") String account);
    @Query(value = "SELECT SUM(comments) FROM loaderdb.marketing_data WHERE TIMESTAMPDIFF(HOUR, STR_TO_DATE(time_stamp, '%Y%m%dT%H:%i'), NOW()) < :hours and account=:Account", nativeQuery = true)
    Integer getSumOfCommentsValues( @Param("hours") int hours,@Param("Account") String account);
    @Query(value = "SELECT SUM(shares) FROM loaderdb.marketing_data WHERE TIMESTAMPDIFF(HOUR, STR_TO_DATE(time_stamp, '%Y%m%dT%H:%i'), NOW()) < :hours and account=:Account", nativeQuery = true)
    Integer getSumOfSharesValues( @Param("hours") int hours,@Param("Account") String account);
}