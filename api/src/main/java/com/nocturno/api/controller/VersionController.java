package com.nocturno.api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(path = "/api")
public class VersionController {

    static class Version {
        public String version;
    }
    
    @GetMapping("/version")
    public ResponseEntity<?> getMethodName() {
        Version version = new Version();
        version.version = "1.0.0";

        return ResponseEntity.ok().body(version);
    }
}
