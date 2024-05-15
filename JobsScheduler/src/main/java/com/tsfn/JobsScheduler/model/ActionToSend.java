package com.tsfn.JobsScheduler.model;

import lombok.Data;

@Data
public class ActionToSend {
    public ActionToSend(String tosend, String notificationText, String actionType, String conditionToCheck,String account_id) {
        this.tosend = tosend;
        this.notifcation_text = notificationText;
        this.action_type = actionType;
        this.condition_tocheck = conditionToCheck;
        this.account_id=account_id;
    }

    private String tosend;
    private String notifcation_text;
    private String action_type;
    private String condition_tocheck;
    private String account_id;

}
