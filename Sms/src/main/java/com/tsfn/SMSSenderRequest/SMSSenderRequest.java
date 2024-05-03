package com.tsfn.SMSSenderRequest;

import lombok.Data;

@Data
public class SMSSenderRequest {
	private String destSMSNumber;
	private String smsMessage;
	
	public SMSSenderRequest (String destSMSNumber, String smsMessage) {
		this.destSMSNumber=destSMSNumber;
		this.smsMessage=smsMessage;
	}
	
}
