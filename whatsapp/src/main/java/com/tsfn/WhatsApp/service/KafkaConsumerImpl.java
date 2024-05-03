package com.tsfn.WhatsApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;




@Service
public class KafkaConsumerImpl {
	 @Autowired
	    private WhatsAppService whatsAppService;
	 
	 @Autowired
	SMSSenderRequestParser parser;
	 
		@KafkaListener(topics = "TEST1" , groupId = "groupId")
		public void listen(String message)
		{
			
			WhatsAppMessageRequest request=parser.parse(message);
			//SMSSenderRequest smssenderrequest =new SMSSenderRequest("+972548148740",message); 
			request.setTo("whatsapp:"+request.getTo());
			System.out.println(request.getTo());
			System.out.println(request.getMessage());
			whatsAppService.sendmsg(request);

			//WhatsAppMessageRequest request = new WhatsAppMessageRequest("whatsapp:+972548148740",message);
			//whatsAppService.sendmsg(request);
			System.out.println("message recieved :"+ message+" from TosfenTopic !!");
		}
}
