package com.tsfn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.tsfn.SMSSenderRequest.SMSSenderRequest;

@Service
public class KafkaConsumerImpl {
	@Autowired
	SMSService smsService;
	
	@Autowired
	SMSSenderRequestParser parser;
	
	@KafkaListener(topics = "TEST1" , groupId = "groupId")
	public void listen(String message)
	{
		SMSSenderRequest smssenderrequest1=parser.parse(message);
	//SMSSenderRequest smssenderrequest =new SMSSenderRequest("+972548148740",message); 
		smsService.sendSMS(smssenderrequest1.getDestSMSNumber(), smssenderrequest1.getSmsMessage());

		System.out.println("message recieved :"+ message+" from TEST1 !!");
	}


}
