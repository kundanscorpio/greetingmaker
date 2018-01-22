package com.britishgas.configuration;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Created by Kundan Sharma
 */
@Component
@Order  (Ordered.HIGHEST_PRECEDENCE)
public class SecurityFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getLocalPort() == 80 && ((HttpServletRequest)servletRequest).getRequestURI().contains("/internal-")) {
            ((HttpServletResponse) servletResponse).sendError(403);
        } else if (servletRequest.getLocalPort() == 8080 && ((HttpServletRequest)servletRequest).getRequestURI().contains("/greeting")) {
            ((HttpServletResponse) servletResponse).sendError(403);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}