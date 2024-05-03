package com.tsfn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tsfn.SMSSenderRequest.SMSSenderRequest;
import com.tsfn.service.SMSService;
import com.tsfn.service.WAPPService;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/api/v1")
@Slf4j
public class SMScontroller {
	
	@Autowired
	SMSService smsService;
	@PostMapping("/processSMS")
	public String process(@RequestBody SMSSenderRequest sendRequest) {
	
		log.info("processSMS starter send request : " + sendRequest.toString());
		return smsService.sendSMS(sendRequest.getDestSMSNumber(), sendRequest.getSmsMessage()) ;
}
	/*
	@Autowired
	WAPPService wappservice;
	@PostMapping("/processWAPP")
	public String process2(@RequestBody SMSSenderRequest sendRequest) {
		System.out.println(sendRequest.toString());
		log.info("processSMS starter send request : " + sendRequest.toString());
		return wappservice.sendWAPP(sendRequest.getDestSMSNumber(), sendRequest.getSmsMessage()) ;
}*/

}
