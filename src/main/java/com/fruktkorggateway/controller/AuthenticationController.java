package com.fruktkorggateway.controller;

import com.common.util.JSON;
import com.common.util.RestCaller;
import com.fruktkorggateway.controller.model.PermissionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("v1/authentication/permission")
public class AuthenticationController {
    private static final RestCaller restCaller = new RestCaller();

    @GetMapping("/{personnummer}")
    @ResponseBody
    public ResponseEntity<?> getPermissions(@PathVariable String personnummer) {
        return JSON.message(restCaller.getCall("http://localhost:12347/v1/authentication/permission/" + personnummer));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<?> addPermission(@RequestBody @Valid PermissionDTO input) {
        return JSON.message(restCaller.postCall("http://localhost:12347/v1/authentication/permission/", input));
    }

    @DeleteMapping("/{personnummer}/permission/{permissionName}")
    @ResponseBody
    public ResponseEntity<?> deletePermission(@PathVariable String personnummer, @PathVariable String permissionName) {
        return JSON.message(restCaller.deleteCall("http://localhost:12347/v1/authentication/permission/" + personnummer + "/permission/" + permissionName));
    }
}
