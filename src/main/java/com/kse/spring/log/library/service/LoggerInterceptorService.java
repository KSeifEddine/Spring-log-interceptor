package com.kse.spring.log.library.service;

import com.kse.spring.log.library.util.StreamHttpServletRequest;
import com.kse.spring.log.library.util.StreamHttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoggerInterceptorService implements HandlerInterceptor {

    private final WriteLog writeLog;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request instanceof StreamHttpServletRequest) {
            writeLog.writeInputStep((StreamHttpServletRequest) request);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        if ((response instanceof StreamHttpServletResponse) && (request instanceof StreamHttpServletRequest)) {
            ((StreamHttpServletResponse) response).setInvokeId(((StreamHttpServletRequest) request).getInvokeId());
            writeLog.writeOutputStep((StreamHttpServletResponse) response);
        }
    }
}