package com.tsfn.Loader.service;

import com.tsfn.Loader.controller.LoaderRequest;
import com.tsfn.Loader.repository.MarketingDataRepository;
import com.tsfn.Loader.service.Threads.InstgramThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Service
public class MarketingDataInstgramService {

    @Autowired
    private MarketingDataRepository marketingDataRepository;
    @Value("${instgram.data.directory}")
    private String directoryPath;
    static private String lastscantime = "2024_03_16T13_30_00";
    private static final Logger logger = LoggerFactory.getLogger(MarketingDataInstgramService.class);

    public void Instgram_Manual_Run(LoaderRequest loaderRequest) {
        int numThreads = Runtime.getRuntime().availableProcessors(); // Number of available CPU cores
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String filename=file.getName();
                    String[] parts = filename.split("_");
                    if(parts[0].compareTo(loaderRequest.getAccountId())==0) {
                        String filedate=parts[2]+"_"+parts[3]+"_"+parts[4].split(("T"))[0];
                        if(filedate.compareTo(loaderRequest.getStartDate())>=0&&filedate.compareTo(loaderRequest.getEndDate())<=0) {
                            if (parts[1].compareTo("instagram") == 0) {
                                logger.info("got to thread creation");
                                executor.submit(new InstgramThread(file,marketingDataRepository));

                            }
                        }
                    }
                }
            }
        }
        executor.shutdown();
    }

    public void hourlyInstgramScan() {
        logger.info("started Hourly Instgram scan.");
        int numThreads = Runtime.getRuntime().availableProcessors(); // Number of available CPU cores
        ExecutorService executor = Executors.newFixedThreadPool(numThreads/3);
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    String filename=file.getName();
                    String[] parts = filename.split("_");

                    String filedate=parts[2]+"_"+parts[3]+"_"+parts[4];
                    if(filedate.compareTo(lastscantime)>0) {
                        if (parts[1].compareTo("instagram") == 0) {
                            logger.info("got to thread creation in scan");
                            executor.submit(new InstgramThread(file,marketingDataRepository));

                        }
                    }

                }
            }
        }
        executor.shutdown();
        //"2024_04_16T13_30_00"
        lastscantime= new SimpleDateFormat("yyyy_MM_dd'T'HH_mm_ss").format(Calendar.getInstance().getTime());
        logger.info("Hourly Instgram scan finished. last modified scan time is "+lastscantime);
    }


}
