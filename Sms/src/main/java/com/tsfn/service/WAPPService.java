package com.tsfn.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service

public class WAPPService {
	
	@Value("${TWILIO_ACCOUNT_SID_W}")
	String ACCOUNT_SID;
	@Value("${TWILIO_AUTH_TOKEN_W}")
	String AUTH_TOKEN;
	@Value("${TWILIO_OUTGOING_WAPP_NUMBER}")
	String OUTGOING_WAPP_NUMBER;

	
	public String sendWAPP(String smsNumber, String smsMessage) {
		
		Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
		Message message = Message.creator(
				new com.twilio.type.PhoneNumber(smsNumber),
				new com.twilio.type.PhoneNumber(OUTGOING_WAPP_NUMBER),
				smsMessage).create();
		return message.getStatus().toString();
	}
}
