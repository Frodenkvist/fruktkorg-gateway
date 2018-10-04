package com.fruktkorggateway.controller;

import com.common.util.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/person")
public class PersonController {
    @GetMapping("/{personnummer}")
    @ResponseBody
    @PreAuthorize("hasAuthority('SEARCH')")
    public ResponseEntity<?> getPerson() {
        return JSON.message(HttpStatus.OK, "Things");
    }
}
