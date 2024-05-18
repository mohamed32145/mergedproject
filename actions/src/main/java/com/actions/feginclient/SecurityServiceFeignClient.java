package com.actions.feginclient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
@Component
@FeignClient(name = "Login-and-Registration", url = "${Login-and-Registration.url}")
public interface SecurityServiceFeignClient {

    @GetMapping("/api/v1/authenticate/validate-token")
    boolean validateToken(@RequestHeader("Authorization") String token);

    @GetMapping("/api/v1/authenticate/user-roles")
    List<String> getUserRoles(@RequestHeader("Authorization") String token);
}
