package com.tsfn.WhatsApp.service;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.http.HttpMethod;
import com.twilio.http.Request;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twilio.exception.ApiException;
import com.twilio.http.Request;
import com.twilio.http.Response;
import com.twilio.rest.api.v2010.account.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Service
public class WhatsAppService {
    public static final String ACCOUNT_SID = "AC2c897f99147d603c9a545c13863a0e84";
    public static final String AUTH_TOKEN = "901faab897369fb5c7d55020e564a0fd";
    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);
    public void sendmsg(WhatsAppMessageRequest request) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        // Replace 'to' and 'from' numbers with the phone numbers you want to use
        String to = request.getTo(); // Replace with the recipient's number
        String from = "whatsapp:+14155238886"; // Replace with your Twilio WhatsApp number

        //add number to participants list



        // Message content
        String messageText = request.getMessage();

        // Send the message
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(from),
                messageText
        ).create();

        logger.info("Message SID: " + message.getSid());
    }

   }
