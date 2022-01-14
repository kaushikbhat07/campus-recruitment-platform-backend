package com.aimit.campushire.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {
    private final String host = "${app.host:default}";

    @CrossOrigin(host)
    @GetMapping("/")
    ResponseEntity home() {
        return new ResponseEntity("Hello World", HttpStatus.OK);
    }
}
