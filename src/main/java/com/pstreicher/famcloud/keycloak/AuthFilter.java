package com.pstreicher.famcloud.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String matchPath = "/sso/login";
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(matchPath);
        if (matcher.matches((HttpServletRequest) servletRequest)){
            log.info("**** Matches ANT *****");
        } else {
            log.info("****-------- DOES NOT MATCH: " + ((HttpServletRequest) servletRequest).getRequestURI());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
