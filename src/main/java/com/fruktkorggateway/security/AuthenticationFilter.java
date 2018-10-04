package com.fruktkorggateway.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String personnummer = request.getHeader(PERSON_NUMBER_HEADER);
        if(personnummer == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        Response authenticationResponse = client.newCall(new Request.Builder()
                .get()
                .url("http://localhost:12347/v1/authentication/permission/" + personnummer)
                .build()
        ).execute();

        if(authenticationResponse.code() == HttpStatus.NOT_FOUND.value()) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        if(authenticationResponse.body() == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        if(authenticationResponse.code() == HttpStatus.OK.value()) {
            List<String> permissions = objectMapper.readValue(authenticationResponse.body().string(), new TypeReference<List<String>>() {});

            Authentication auth = new AuthenticationToken(personnummer, permissions);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
