package com.tsfn.JobsScheduler.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsfn.JobsScheduler.model.ActionToSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import java.lang.reflect.Type;
import java.util.List;

@Service
public class KafkaConsumerImpl {
    @Autowired
    private JobSchedulerservice jobSchedulerservice;

    private Gson gson = new Gson();


    @KafkaListener(topics = "ActionTocheckk" , groupId = "groupId")
    public void listen(String message)
    {



        System.out.println("message recieved :"+ message+" from TosfenTopic !!");
        ActionToSend recivedaction=gson.fromJson(message,ActionToSend.class);
        System.out.println("message after json is :"+recivedaction.toString());

//        Type listType = new TypeToken<List<List<Integer>>>() {}.getType();
//        List<List<Integer>> receivedListOfConds = gson.fromJson(recivedaction.getCondition_tocheck(), listType);
//        System.out.println("the list of conditions is :"+receivedListOfConds.toString());
        jobSchedulerservice.proccessAction(recivedaction);

        System.out.println("-----------------------------------");



    }
}
