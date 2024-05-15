package com.tsfn.JobsScheduler.model;

import lombok.Data;

@Data
public class NotificationToSend {
    private String to;
    private String message;

    // Constructor


    public NotificationToSend(String to, String message) {

        this.to=to;
        this.message=message;
    }
}
