package com.fruktkorggateway.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

public class AuthenticationToken extends AbstractAuthenticationToken {

    private String personnummer;

    public AuthenticationToken(String personnummer, List<String> permissions) {
        super(permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        this.personnummer = personnummer;
    }

    @Override
    public Object getCredentials() {
        return personnummer;
    }

    @Override
    public Object getPrincipal() {
        return personnummer;
    }
}
