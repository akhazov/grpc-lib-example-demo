package com.akhazov.gateway.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class MdcContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try(MDC.MDCCloseable mdcCloseable = MDC.putCloseable("RqUid", request.getHeader("RqUid"))) {
            log.debug("REQUEST:%s %s &%s, body: \n%s".formatted(request.getMethod(), request.getRequestURI(), request.getQueryString(), new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8)));
            filterChain.doFilter(request, response);
            log.debug("RESPONSE: status: %d".formatted(response.getStatus()));
        }
    }

}
