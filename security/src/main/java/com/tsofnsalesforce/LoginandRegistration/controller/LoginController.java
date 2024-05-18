package com.tsofnsalesforce.LoginandRegistration.controller;

import com.tsofnsalesforce.LoginandRegistration.Response.AuthenticationResponse;
import com.tsofnsalesforce.LoginandRegistration.request.AuthenticationRequest;
import com.tsofnsalesforce.LoginandRegistration.service.AuthenticationService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/api/v1/authenticate")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/activate-account")
    public void activateAccount(@RequestParam String token) throws MessagingException {
        authenticationService.activateAccount(token);
    }

    @GetMapping("/validate-token")
    boolean validateToken(@RequestHeader("Authorization") String token){
        return authenticationService.validateToken(token);
    }

    @GetMapping("/user-roles")
    public List<String> getUserRoles(@RequestHeader("Authorization") String token) {
        return authenticationService.getUserRoles(token);
    }
}
