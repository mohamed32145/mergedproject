package com.tsfn.WhatsApp.controller;

import com.tsfn.WhatsApp.service.WhatsAppMessageRequest;
import com.tsfn.WhatsApp.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/whatsapp")
public class WhatsAppController {

    @Autowired
    private WhatsAppService whatsAppService;

    @PostMapping("/send")
    public ResponseEntity<String> sendmsg(@RequestBody WhatsAppMessageRequest request){
        whatsAppService.sendmsg(request);

        return ResponseEntity.ok("message sent!");
    }
}
