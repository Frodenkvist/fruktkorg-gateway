package com.fruktkorggateway.security;

import com.fruktkorggateway.common.exception.PersonMissingException;
import com.fruktkorggateway.common.model.Person;
import com.fruktkorggateway.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {
    private static final String PERSON_NUMBER_HEADER = "X-PERSONR";

    @Autowired
    private PersonService personService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String personNumber = request.getHeader(PERSON_NUMBER_HEADER);
        if (personNumber == null) {
            throw new SecurityException("X-PERSONR Was missing in Header");
        }

        Person loggedInUser;
        try {
            loggedInUser = personService.getPerson(personNumber);
        } catch (PersonMissingException e) {
            throw new SecurityException("Person was not found");
        }

        Authentication auth = new AuthenticationToken(loggedInUser);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }
}
