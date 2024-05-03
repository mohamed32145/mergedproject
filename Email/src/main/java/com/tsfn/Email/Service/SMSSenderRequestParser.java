package com.tsfn.Email.Service;

import org.springframework.stereotype.Component;

import com.tsfn.Email.Details.EmailDetails;

@Component
public class SMSSenderRequestParser {
    public EmailDetails parse(String input) {
        String destSMSNumber = extractValue(input, "to");
        String smsMessage = extractValue(input, "message");

        return new EmailDetails(destSMSNumber, smsMessage,"MESSAGE",null);
    }
    private String extractValue(String input, String fieldName) {
        int startIndex = input.indexOf(fieldName + "=");
        if (startIndex == -1) {
            throw new IllegalArgumentException("Field " + fieldName + " not found in the input string");
        }
        startIndex += fieldName.length() + 1; // Move index to the beginning of the value
        int endIndex = input.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = input.indexOf(")", startIndex);
        }
        return input.substring(startIndex, endIndex).trim();
    }
}
