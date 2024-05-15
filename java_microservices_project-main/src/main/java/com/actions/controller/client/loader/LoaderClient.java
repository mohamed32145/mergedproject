package com.actions.controller.client.loader;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "loader-service",url = "http://localhost:7070")
public interface LoaderClient {

    @PostMapping("/Loader/manual-run/{loaderName}")
    public ResponseEntity<String> scanfiles(@PathVariable String loaderName,
                                       @RequestBody LoaderRequest loaderRequest);
    @PostMapping("/Loader/enable/{loaderName}")
    public ResponseEntity<String> enableScan(@PathVariable String loaderName);

    @PostMapping("/Loader/disable/{loaderName}")
    public ResponseEntity<String> disableScan(@PathVariable String loaderName);
}
