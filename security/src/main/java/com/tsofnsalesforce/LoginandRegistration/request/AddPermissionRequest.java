package com.tsofnsalesforce.LoginandRegistration.request;

import com.tsofnsalesforce.LoginandRegistration.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddPermissionRequest {

    private String email;
    private List<Role> roles;
}
