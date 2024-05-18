package com.tsofnsalesforce.LoginandRegistration.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotEmpty(message = "First Name Can't be empty!")
    @NotBlank(message = "First Name Can't be empty!")
    private String firstname;
    @NotEmpty(message = "Last Name Can't be empty!")
    @NotBlank(message = "Last Name Can't be empty!")
    private String lastname;
    @NotEmpty(message = "Email Can't be empty!")
    @NotBlank(message = "Email Can't be empty!")
    private String email;
    @NotEmpty(message = "Account name can't be empty!")
    @NotBlank(message = "Account name be empty!")
    private String accountName;
    @NotEmpty(message = "Password Can't be empty!")
    @NotBlank(message = "Password Can't be empty!")
    @Size(min = 8,message = "Password should be 8 or more character!")
    private String password;
}
