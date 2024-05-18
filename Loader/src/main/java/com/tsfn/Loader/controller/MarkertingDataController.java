package com.tsfn.Loader.controller;

import com.tsfn.Loader.feignclient.SecurityServiceFeignClient;
import com.tsfn.Loader.service.MarketingDataFacebookService;
import com.tsfn.Loader.service.MarketingDataInstgramService;
import com.tsfn.Loader.service.MarketingDataLinkedInService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

@RestController
@RequestMapping("/Loader")

public class MarkertingDataController implements SchedulingConfigurer {
    @Autowired
    private MarketingDataLinkedInService marketingDataLinkedInService;
    @Autowired
    private MarketingDataInstgramService marketingDataInstgramService;

    @Autowired
    private MarketingDataFacebookService marketingDataFacebookService;

    @Autowired
    private SecurityServiceFeignClient securityServiceFeignClient;

    private  boolean isLinkedInTimerOn = false;
    private  boolean isInstgramInTimerOn = false;
    private  boolean isFacebookInTimerOn = false;
    private ScheduledFuture<?> scheduledTask;
    private static final Logger logger = LoggerFactory.getLogger(MarketingDataLinkedInService.class);
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {


    }
    @PostMapping("/manual-run/{loaderName}")
    public ResponseEntity<String> scanfiles(@RequestHeader("Authorization") String token,@PathVariable String loaderName,
                                       @RequestBody LoaderRequest loaderRequest){
        if(!securityServiceFeignClient.validateToken(token))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired token");
        List<String> roles=securityServiceFeignClient.getUserRoles(token);
        if(!roles.contains("ADMIN")&&!roles.contains("TRIGGER_MANUAL_SCAN"))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid or expired token");
        try {
            if (loaderName.compareTo("linkedin")==0) {
                marketingDataLinkedInService.linked_In_Manual_Run(loaderRequest);
            }
            if (loaderName.compareTo("Instgram")==0) {
                marketingDataInstgramService.Instgram_Manual_Run(loaderRequest);
            }
            if (loaderName.compareTo("facebook")==0) {
                marketingDataFacebookService.Facebook_Manual_Run(loaderRequest);
            }
            return ResponseEntity.ok("Loader " + loaderName + " executed successfully."+loaderRequest.getAccountId());
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/enable/{loaderName}")
    public ResponseEntity<String> enableScan(@PathVariable String loaderName) {
        if ("linkedin".equalsIgnoreCase(loaderName)) {
            isLinkedInTimerOn = true;
            scheduledTask();
            return ResponseEntity.ok("Scans enabled for loader: " + loaderName);
        }
        if ("Instgram".equalsIgnoreCase(loaderName)) {
            isInstgramInTimerOn = true;
            scheduledTask();
            return ResponseEntity.ok("Scans enabled for loader: " + loaderName);
        }
        if ("facebook".equalsIgnoreCase(loaderName)) {
            isFacebookInTimerOn = true;
            scheduledTask();
            return ResponseEntity.ok("Scans enabled for loader: " + loaderName);
        }
        return ResponseEntity.ok("Scans not enabled for loader: " + loaderName);
    }

    @Scheduled(fixedRate = 1000*3600) // Run every 10 seconds
    public void scheduledTask() {
        if (isLinkedInTimerOn) {
            marketingDataLinkedInService.hourlyLinkedInScan();
        }
        if (isInstgramInTimerOn) {
            marketingDataInstgramService.hourlyInstgramScan();
        }
        if (isFacebookInTimerOn) {
            marketingDataFacebookService.hourlyFacebookScan();
        }
    }

    @PostMapping("/disable/{loaderName}")
    public ResponseEntity<String> disableScan(@PathVariable String loaderName) {
        if ("linkedin".equalsIgnoreCase(loaderName)) {
            isLinkedInTimerOn = false;
            if (scheduledTask != null) {
                scheduledTask.cancel(true);
            }
            return ResponseEntity.ok("Scans disabled for loader: " + loaderName);
        }
        if ("Instgram".equalsIgnoreCase(loaderName)) {
            isInstgramInTimerOn = false;
            if (scheduledTask != null) {
                scheduledTask.cancel(true);
            }
            return ResponseEntity.ok("Scans disabled for loader: " + loaderName);
        }
        if ("facebook".equalsIgnoreCase(loaderName)) {
            isFacebookInTimerOn = false;
            if (scheduledTask != null) {
                scheduledTask.cancel(true);
            }
            return ResponseEntity.ok("Scans disabled for loader: " + loaderName);
        }
        return ResponseEntity.ok("Scans not disabled for loader: " + loaderName);
    }
}
