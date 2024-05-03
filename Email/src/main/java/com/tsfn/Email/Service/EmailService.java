package com.tsfn.Email.Service;

import com.tsfn.Email.Details.EmailDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class EmailService{

	@Autowired 
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}") 
	private String sender;

	public String sendSimpleMail(@RequestBody EmailDetails details)
	{

			SimpleMailMessage mailMessage = new SimpleMailMessage();

			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());

			javaMailSender.send(mailMessage);
			System.out.println("email sent");
			return "Mail Sent Successfully...";
		}

}
