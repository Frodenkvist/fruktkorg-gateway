package com.fruktkorggateway.controller;

import com.common.util.JSON;
import com.common.util.RestCaller;
import com.fruktkorggateway.controller.model.PersonDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/person")
public class PersonController {
    private static final RestCaller restCaller = new RestCaller();

    @GetMapping("/{personnummer}")
    @ResponseBody
    @PreAuthorize("hasAuthority('SEARCH')")
    public ResponseEntity<?> getPerson(@PathVariable String personnummer) {
        return JSON.message(restCaller.getCall("http://localhost:12346/v1/person/" + personnummer));
    }

    @GetMapping
    @ResponseBody
    @PreAuthorize("hasAuthority('SEARCH')")
    public ResponseEntity<?> getPersons() {
        return JSON.message(restCaller.getCall("http://localhost:12346/v1/person/"));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SEARCH')")
    public ResponseEntity<?> createPerson(@RequestBody PersonDTO person) {
        System.out.println("THIS IS THE CREATE!");
        return JSON.message(restCaller.postCall("http://localhost:12346/v1/person/", person));
    }

    @DeleteMapping("/{personnummer}")
    @PreAuthorize("hasAuthority('SEARCH')")
    public ResponseEntity<?> deletePerson(@PathVariable String personnummer) {
        return JSON.message(restCaller.deleteCall("http://localhost:12346/v1/person/" + personnummer));
    }
}
