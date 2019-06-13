package ru.nikitamedvedev.application.service.security;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtHttpFilter implements Filter {

    private final JwtService jwtService;

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) {
        val httpServletRequest = (HttpServletRequest) servletRequest;

        log.info("Got request: {} with header: {}",
                httpServletRequest.getRequestURI(),
                httpServletRequest.getHeader("Authorization"));
        if (jwtService.hasAccess(httpServletRequest.getRequestURI(), httpServletRequest.getHeader("Authorization"))) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.info("bad request");
            val response = (HttpServletResponse) servletResponse;
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
