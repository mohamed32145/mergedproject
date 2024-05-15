package com.tsfn.JobsScheduler.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tsfn.JobsScheduler.model.ActionToSend;
import com.tsfn.JobsScheduler.model.MetricsTable;
import com.tsfn.JobsScheduler.repository.MarketingDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class JobSchedulerservice {
    @Autowired
    private KafkaProducerImpl kafkaProducerImpl;
    @Autowired
    private MarketingDataRepository repository;
    public void proccessAction(ActionToSend recivedaction) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<List<Integer>>>() {}.getType();
        List<List<Integer>> receivedListOfConds = gson.fromJson(recivedaction.getCondition_tocheck(), listType);
        boolean doesCondsHold=proccessConditions(receivedListOfConds,recivedaction.getAccount_id());
        System.out.println("condition calculation value is: "+doesCondsHold);
        if (doesCondsHold){
            String action_type= recivedaction.getAction_type();
            String message = "to="+recivedaction.getTosend()+", message="+recivedaction.getNotifcation_text()+",";
            if (action_type.equalsIgnoreCase("sms")){
                // do sms send and created the appropraite message
                //kafkaProducerImpl.sendSMS(message);
                kafkaProducerImpl.sendSMS(message);
                System.out.println("sending sms message");

            } else if (action_type.equalsIgnoreCase("email")) {
                //do email send and created the appropraite message
                //kafkaProducerImpl.sendMAIL(message);
                //String message = "to="+recivedaction.getTosend()+", message="+recivedaction.getNotifcation_text()+",";
                kafkaProducerImpl.sendMAIL(message);
                System.out.println("sending email message");


            } else if (action_type.equalsIgnoreCase("whatsapp")) {
                // do whatsapp send and created the appropraite message
                //kafkaProducerImpl.sendWHATS(message);
                kafkaProducerImpl.sendWHATS(message);
                System.out.println("sending whatsapp message");
            }

        }
    }

    private boolean proccessConditions(List<List<Integer>> receivedListOfConds,String account_id) {
        for (List<Integer> receivedListOfCond : receivedListOfConds) {
            boolean clauseval=proccessClause(receivedListOfCond,account_id);
            System.out.println("clause: "+receivedListOfCond.toString()+" returned "+clauseval);
            if (clauseval)
                return true;
        }
        return false;
    }

    private boolean proccessClause(List<Integer> clause,String account_id) {
        for (int i = 0; i < clause.size(); i++) {
            boolean metricval=checkMetric(clause.get(i),account_id);
            System.out.println("metric: "+clause.get(i)+" returned "+metricval);
            if(!metricval)
                return false;
        }
        return true;
    }
    private boolean checkMetric(Integer integer,String account_id) {
        boolean outpot=false;
        Integer sum=0;
        MetricsTable metric=getMetricById(integer);
        System.out.println(metric.getMetric().toLowerCase().length() +" ----- "+metric.getTimeFrameHours());
        if (integer>=1&&integer<=4)
            sum=repository.getSumOfimpressionsValues( metric.getTimeFrameHours(),account_id);
        if (integer>=5&&integer<=8)
            sum=repository.getSumOfviewsValues( metric.getTimeFrameHours(),account_id);
        if (integer>=9&&integer<=12)
            sum=repository.getSumOfclicksValues( metric.getTimeFrameHours(),account_id);
        if (integer>=13&&integer<=16)
            sum=repository.getSumOflikesValues( metric.getTimeFrameHours(),account_id);
        if (integer>=17&&integer<=20)
            sum=repository.getSumOfCommentsValues( metric.getTimeFrameHours(),account_id);
        if (integer>=21&&integer<=24)
            sum=repository.getSumOfSharesValues( metric.getTimeFrameHours(),account_id);
        System.out.println("the metric is: "+metric.toString()+metric.getMetric().toLowerCase()+"  and the sum is: "+sum);
        if (sum!=null&&sum>metric.getThreshold())
            outpot=true;
        return outpot;
    }
    // Method to get enum constant by ID
    private static MetricsTable getMetricById(int id) {
        for (MetricsTable metric : MetricsTable.values()) {
            if (metric.getId() == id) {
                return metric;
            }
        }
        return null; // ID not found
    }



}
