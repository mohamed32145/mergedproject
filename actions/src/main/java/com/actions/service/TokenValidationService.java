package com.actions.service;

import com.actions.feginclient.SecurityServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenValidationService {

    @Autowired
    private SecurityServiceFeignClient securityServiceFeignClient;

    public boolean isValidToken(String token) {
//        return securityServiceFeignClient.validateToken("Bearer " + token);
        return securityServiceFeignClient.validateToken(token);
    }

    public List<String> getUserRoles(String token) {
        return securityServiceFeignClient.getUserRoles(token);
    }

}
