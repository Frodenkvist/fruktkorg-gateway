package com.fruktkorggateway.security;

import com.common.util.RestCaller;
import com.common.util.RestResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class AuthenticationFilter extends GenericFilterBean {
    private static final String PERSON_NUMBER_HEADER = "X-PERSONR";
    private static final RestCaller restCaller = new RestCaller();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String personnummer = request.getHeader(PERSON_NUMBER_HEADER);
        if(personnummer == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        RestResponse<List<String>> restResponse = restCaller.getCall("http://localhost:12347/v1/authentication/permission/" + personnummer,
                new TypeReference<List<String>>() {});

        if(restResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        if(restResponse.getResponse() == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        if(restResponse.isOk()) {
            Authentication auth = new AuthenticationToken(personnummer, restResponse.getResponse());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
