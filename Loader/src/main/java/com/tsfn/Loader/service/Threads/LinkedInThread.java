package com.tsfn.Loader.service.Threads;
import com.opencsv.CSVReader;
import com.tsfn.Loader.model.MarketingData;
import com.tsfn.Loader.repository.MarketingDataRepository;
import com.tsfn.Loader.service.MarketingDataLinkedInService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
@Setter
@Getter
public class LinkedInThread implements Runnable{
    private MarketingDataRepository marketingDataRepository;
    private static final Logger logger = LoggerFactory.getLogger(LinkedInThread.class);
   private File myfile;
    public LinkedInThread(File myfile,MarketingDataRepository marketingDataRepository) {
        super();
        this.myfile = myfile;
        this.marketingDataRepository=marketingDataRepository;
    }
    @Override
    public void run() {
        List<List<String>> records = new ArrayList<>();
        String timeStamp = new SimpleDateFormat("yyyyMMdd'T'HH:mm").format(Calendar.getInstance().getTime());
        String account_id=myfile.getName().split("_")[0];
        try (CSVReader csvReader = new CSVReader(new FileReader(myfile));) {
            String[] values ;
            csvReader.readNext();
            csvReader.readNext();
            while ((values = csvReader.readNext()) != null) {
                for (int i = 0; i < values.length; i++)
                    if (values[i].equals("NaN")||values[i]==null|| values[i].isEmpty())
                        values[i] = "0";
                records.add(Arrays.asList(values));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        logger.info(records.size()+"");
        for (List<String> dataunit : records) {
            Optional<MarketingData> checkifexist = marketingDataRepository.findByPostidAndAccount(dataunit.get(1), account_id);
            logger.info(dataunit.get(1)+" "+ account_id);
            MarketingData marketingData = null;
            if (!checkifexist.isPresent()) {
                logger.info("got here");
                logger.info(dataunit.get(10));
                int views = Integer.parseInt(dataunit.get(10)) + Integer.parseInt(dataunit.get(11));
                logger.info("got here2");
                marketingData = new MarketingData(account_id, timeStamp, dataunit.get(1), dataunit.get(2), Integer.parseInt(dataunit.get(9)), views,
                        Integer.parseInt(dataunit.get(12)), Double.parseDouble(dataunit.get(13)), Integer.parseInt(dataunit.get(14)),
                        Integer.parseInt(dataunit.get(15)), Integer.parseInt(dataunit.get(16)), Double.parseDouble(dataunit.get(18)));
                logger.info("got here2");
                logger.info(marketingData.toString());
                marketingDataRepository.save(marketingData);
            } else {
                MarketingData founddata = checkifexist.get();
                founddata.setClicks(Integer.parseInt(dataunit.get(12)));
                founddata.setComments(Integer.parseInt(dataunit.get(15)));
                founddata.setLikes(Integer.parseInt(dataunit.get(14)) );
                founddata.setImpressions(Integer.parseInt(dataunit.get(9)) );
                founddata.setShares(Integer.parseInt(dataunit.get(16)) );
                founddata.setClickThroughRate(Double.parseDouble(dataunit.get(13)));
                founddata.setEngagementRate(Double.parseDouble(dataunit.get(18)));
                founddata.setTimeStamp(timeStamp);
                marketingDataRepository.save(founddata);
            }
        }
    }
}
