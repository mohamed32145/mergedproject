package com.tsofnsalesforce.LoginandRegistration.controller;

import com.tsofnsalesforce.LoginandRegistration.request.AddAccountRequest;
import com.tsofnsalesforce.LoginandRegistration.request.AddPermissionRequest;
import com.tsofnsalesforce.LoginandRegistration.request.DeletePermissionRequest;
import com.tsofnsalesforce.LoginandRegistration.request.RegisterRequest;
import com.tsofnsalesforce.LoginandRegistration.service.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(@RequestBody @Valid  RegisterRequest request) throws MessagingException {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/add-account")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> addAccount(@RequestBody @Valid AddAccountRequest request){
        authenticationService.addAccount(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/add-permission")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updatePermission(@RequestBody @Valid AddPermissionRequest request) throws MessagingException {
        authenticationService.AddPermission(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/delete-permission")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> deletePermission(@RequestBody @Valid DeletePermissionRequest request) throws MessagingException {
        authenticationService.deletePermission(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        authenticationService.refreshToken(httpServletRequest,httpServletResponse);
    }

}