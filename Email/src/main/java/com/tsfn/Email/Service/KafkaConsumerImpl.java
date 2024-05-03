package com.tsfn.Email.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.tsfn.Email.Details.EmailDetails;

@Service
public class KafkaConsumerImpl {
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	SMSSenderRequestParser parser;

	@KafkaListener(topics = "TEST1" , groupId = "groupId")
	public void listen(String message)
	{
		
		EmailDetails emaildetails=parser.parse(message);
		//fix the messge 
		//EmailDetails emaildetails =new EmailDetails("aseelk5@gmail.com",message,"a",null);
		String status = emailService.sendSimpleMail(emaildetails);
		//SimpleMailMessage mailMessage = new SimpleMailMessage();

	
		
		System.out.println("message recieved :"+ message+" from TosfenTopic !!");
	}

}
