package com.tsfn.WhatsApp.service;

public class WhatsAppMessageRequest {
    private String to;
    private String message;

    // Constructor
    public WhatsAppMessageRequest() {
    }

    public WhatsAppMessageRequest(String to, String message) {
		// TODO Auto-generated constructor stub
    	this.to=to;
    	this.message=message;
	}

	// Getters and setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}