package com.kse.spring.log.library.service;

import com.kse.spring.log.library.util.StreamHttpServletRequest;
import com.kse.spring.log.library.util.StreamHttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestFilter implements Filter {

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
        StreamHttpServletRequest streamHttpServletRequest = new StreamHttpServletRequest((HttpServletRequest) servletRequest);
        StreamHttpServletResponse streamHttpServletResponse = new StreamHttpServletResponse((HttpServletResponse) servletResponse);
        filterChain.doFilter(streamHttpServletRequest, streamHttpServletResponse);
    }
}