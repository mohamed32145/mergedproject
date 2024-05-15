package com.actions.Scheduler;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.actions.controller.ActionsController;
import com.actions.model.ActionToSend;
import com.actions.model.action;
import com.actions.repository.ActionRepository;
import com.actions.service.ActionsService;
import com.actions.service.KafkaProducerImpl;
import com.google.gson.Gson;



@Component
public final  class ActionScheduler implements Runnable, InitializingBean, DisposableBean{
	@Autowired
	private ActionRepository actionRepository;
    
    @Autowired
    private KafkaProducerImpl kafkaProducerImpl;
    @Autowired
    private ActionsService acionservice;
	
    @Autowired
    private ActionsController controller;

    private boolean running;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public boolean start() { ////fix the timmer :)
         if (!running) {
            running = true;
            ZonedDateTime now = ZonedDateTime.now(ZoneId.systemDefault());
            ZonedDateTime nextRun = now.withHour(0).withMinute(0).withSecond(0);
            if (now.compareTo(nextRun) > 0)
                nextRun = nextRun.plusHours(1); //fix it to 1 hour or 60 min
            Duration duration = Duration.between(now, nextRun);
            scheduler.scheduleAtFixedRate(this, 0, 1,
                    TimeUnit.HOURS);
           
           return true;
        }
        return false;
    }

    public boolean stop() {
        if (running) {
            try {
                scheduler.shutdown();
                if (!scheduler.awaitTermination(5, TimeUnit.MINUTES)) {
                    return false;
                }
                running = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public void run() { //fix 
        // Implement your task here
        LocalDateTime now = LocalDateTime.now();
        int currentHour = now.get(ChronoField.HOUR_OF_DAY); // Get current hour
//        System.out.println("current hour " + currentHour);
        DayOfWeek currentDayOfWeek = now.getDayOfWeek(); // Get current day of week
        int day_num=(currentDayOfWeek.getValue()+1)%7;
       // if(day_num == 8) day_num=1; important
        
        System.out.println("current day " + day_num);
        List<action> actions= actionRepository.findAll();
        actions.forEach(action -> {
//        	System.out.println("pre condition");
//        	System.out.println("run on day "+ action.getRun_on_day());
//        	System.out.println("enabledd "+ action.getIs_enabled());
//        	System.out.println("hoyurrrrr "+ action.getRun_on_time());
            String[] parts = action.getRun_on_time().split(":|\\s");
            // Parse the hour part and return as an integer
            int hour_tocheck= Integer.parseInt(parts[0]);
//            System.out.println("check the hour  " + hour_tocheck);


        	if((action.getRun_on_day()==day_num || (day_num==8 && action.getRun_on_day()==1))&& action.getIs_enabled()==true && hour_tocheck==currentHour ) {
        		System.out.println("in condition");
        		Gson gson = new Gson();
    			Map<String, Object> metric = action.getCondition();
    			List<List<Integer>> metrics = acionservice.getmetric(metric);
        		String json = gson.toJson(metrics);
        		
        		//String cond= controller.getCondition(action.getId()).getBody().toString();
        		ActionToSend actiontosend = new ActionToSend(action.getTo_reciver(),action.getNotification_text(),action.getAction_type(),json,action.getAccount_id());
//        		System.out.println(actiontosend.toString());
        		
                kafkaProducerImpl.sendMessage(gson.toJson(actiontosend)); //sending to kafka
        		

        	}
        	
         
        });
        
		System.out.println("the end");


        
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (start()) {
            System.out.println("ActionScheduler thread is running.");
            return;
        }
        System.err.println("ActionScheduler thread encountered an error and is not running.");
    }

    @Override
    public void destroy() throws Exception {
        if (stop()) {
            System.out.println("ActionScheduler thread is closed.");
            return;
        }
        System.err.println("ActionScheduler thread encountered an error and is not closed.");
    }
}
