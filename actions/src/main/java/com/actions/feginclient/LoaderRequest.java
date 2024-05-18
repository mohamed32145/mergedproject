package com.actions.feginclient;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoaderRequest {
    private String accountId;
    private String startDate;
    private String endDate;
}