package com.tsfn.Email.Controller;

import com.tsfn.Email.Details.EmailDetails;
import com.tsfn.Email.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

	@RestController
	@RequestMapping("/api/v1")
	public class EmailController {

		@Autowired
		private EmailService emailService;

		@PostMapping("/sendMail")
		public String
		sendMail(@RequestBody EmailDetails details)
		{
			String status = emailService.sendSimpleMail(details);

			return status;
		}

	}
	
