package com.tsfn.JobsScheduler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerImpl {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    public void sendMessage(String message)
    {
        kafkaTemplate.send("TEST1", message);
    }
    public void sendSMS(String message)
    {
        kafkaTemplate.send("Sms", message);
    }
    public void sendWHATS(String message)
    {
        kafkaTemplate.send("Whatsapp", message);
    }
    public void sendMAIL(String message)
    {
        kafkaTemplate.send("ail", message);
    }
}
