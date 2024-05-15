package com.actions.controller.client.loader;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class LoaderRequest {
    private String accountId;
    private String startDate;
    private String endDate;
}
