package com.tsfn.Loader.repository;

import com.tsfn.Loader.model.MarketingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MarketingDataRepository extends JpaRepository<MarketingData,Integer> {
    //public boolean existsByPost_IDAndTimeStamp(String timeStamp);
     Optional<MarketingData> findByPostidAndAccount(String postid,String account);

}
