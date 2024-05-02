package com.tsfn.Loader.service.Threads;

import com.opencsv.CSVReader;
import com.tsfn.Loader.model.MarketingData;
import com.tsfn.Loader.repository.MarketingDataRepository;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;
@Setter
@Getter
public class InstgramThread implements Runnable{
    private MarketingDataRepository marketingDataRepository;

    private File myfile;
    public InstgramThread(File myfile,MarketingDataRepository marketingDataRepository) {
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
                    if (values[i] == null || values[i].compareTo("")==0)
                        values[i] = "0";
                records.add(Arrays.asList(values));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        for (List<String> dataunit : records) {
            Optional<MarketingData> checkifexist = marketingDataRepository.findByPostidAndAccount(dataunit.get(1), account_id);
            MarketingData marketingData = null;
            if (!checkifexist.isPresent()) {
                String post_Id=dataunit.get(0);
                String content_Type=dataunit.get(8);
                int impression=Integer.parseInt(dataunit.get(11));
                int views = Integer.parseInt(dataunit.get(12));
                int clicks=Integer.parseInt(dataunit.get(17));
                double clickTR= ((double) clicks) /impression;
                int likes=Integer.parseInt(dataunit.get(15));
                int comments=Integer.parseInt(dataunit.get(16));
                int shares=Integer.parseInt(dataunit.get(13));
                double engagmentRate=((double)(likes+comments+shares)/views);
                marketingData = new MarketingData(account_id, timeStamp, post_Id, content_Type,impression , views,clicks
                        , clickTR,likes , comments, shares, engagmentRate);
                marketingDataRepository.save(marketingData);
            } else {
                MarketingData founddata = checkifexist.get();
                founddata.setClicks(Integer.parseInt(dataunit.get(17)));
                founddata.setComments(Integer.parseInt(dataunit.get(16)));
                founddata.setLikes(Integer.parseInt(dataunit.get(15)));
                founddata.setImpressions(Integer.parseInt(dataunit.get(11)));
                founddata.setShares(Integer.parseInt(dataunit.get(13)));
                founddata.setClickThroughRate((double) Integer.parseInt(dataunit.get(17)) /Integer.parseInt(dataunit.get(11)));
                founddata.setEngagementRate(((double)(Integer.parseInt(dataunit.get(15))+Integer.parseInt(dataunit.get(16))
                        +Integer.parseInt(dataunit.get(13)))/Integer.parseInt(dataunit.get(12))));
                marketingDataRepository.save(founddata);
            }
        }
    }
}
