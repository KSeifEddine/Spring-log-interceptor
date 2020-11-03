package com.kse.spring.log.library.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kse.spring.log.library.model.*;
import com.kse.spring.log.library.model.enumeration.StepLog;
import com.kse.spring.log.library.util.StreamHttpServlet;
import com.kse.spring.log.library.util.StreamHttpServletRequest;
import com.kse.spring.log.library.util.StreamHttpServletResponse;
import com.kse.spring.log.library.wrapper.ServletRequestWrapper;
import com.kse.spring.log.library.wrapper.ServletResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WriteLog {

    @Value("application.name")
    private String applicationName;

    private final ServletRequestWrapper servletRequestWrapper;
    private final ServletResponseWrapper servletResponseWrapper;

    private static Logger logger = LogManager.getLogger("custom-log");

    private void writeInvokeLogToFileLog(InvokeLog invokeLog) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String invokeLogAsJsonString = mapper.writeValueAsString(invokeLog);
        logger.debug(invokeLogAsJsonString);
    }

    void writeInputStep(StreamHttpServletRequest request) throws IOException {
        InvokeLog invokeLog = writeCommonInvokeLog(request);
        invokeLog.setStep(StepLog.INPUT);
        invokeLog.setRequest(writeRequestQueryLog(request));
        writeInvokeLogToFileLog(invokeLog);
    }

    void writeOutputStep(StreamHttpServletResponse response) throws IOException {
        InvokeLog invokeLog = writeCommonInvokeLog(response);
        invokeLog.setStep(StepLog.OUTPUT);
        invokeLog.setResponse(writeResponseQueryLog(response));
        writeInvokeLogToFileLog(invokeLog);
    }

    private InvokeLog writeCommonInvokeLog(StreamHttpServlet servlet) {
        InvokeLog invokeLog = new InvokeLog();
        invokeLog.setInvokeId(servlet.getInvokeId());
        invokeLog.setApplicationName(applicationName);
        invokeLog.setDate(LocalDateTime.now());
        return invokeLog;
    }

    private RequestQueryLog writeRequestQueryLog(StreamHttpServletRequest request) throws IOException {
        if (request == null)
            return null;
        RequestQueryLog requestQueryLog = new RequestQueryLog();
        requestQueryLog.setMethod(request.getMethod());
        requestQueryLog.setUrl(request.getRequestURL() != null ? request.getRequestURL().toString() : "");
        requestQueryLog.setParams(servletRequestWrapper.getParameters(request));
        requestQueryLog.setSessionId(request.getRequestedSessionId());
        requestQueryLog.setClient(writeClientLog(request));
        requestQueryLog.setHeader(writeQueryHeaderLog(request));
        requestQueryLog.setBody(writeQueryBodyLog(request));
        return requestQueryLog;
    }

    private ResponseQueryLog writeResponseQueryLog(StreamHttpServletResponse response) throws IOException {
        ResponseQueryLog responseQueryLog = new ResponseQueryLog();
        responseQueryLog.setHeader(writeQueryHeaderLog(response));
        responseQueryLog.setBody(writeQueryBodyLog(response));
        return responseQueryLog;
    }

    private ClientLog writeClientLog(StreamHttpServletRequest request) {
        ClientLog clientLog = new ClientLog();
        clientLog.setAdress(servletRequestWrapper.getRemoteHostFromRequest(request));
        return clientLog;
    }

    private QueryHeaderLog writeQueryHeaderLog(StreamHttpServlet servlet) {
        if (servlet == null)
            return null;
        QueryHeaderLog queryHeaderLog = new QueryHeaderLog();
        if (servlet instanceof StreamHttpServletRequest)
            queryHeaderLog.setAttributes(servletRequestWrapper.getHeaderAttributes(servlet));
        else if (servlet instanceof StreamHttpServletResponse)
            queryHeaderLog.setAttributes(servletResponseWrapper.getHeaderAttributes(servlet));
        return queryHeaderLog;
    }

    private QueryBodyLog writeQueryBodyLog(StreamHttpServlet servlet) throws IOException {
        if (servlet == null)
            return null;
        QueryBodyLog queryBodyLog = new QueryBodyLog();
        if (servlet instanceof StreamHttpServletRequest)
            queryBodyLog.setPayload(servletRequestWrapper.getBodyPayload(servlet));
        else if (servlet instanceof StreamHttpServletResponse)
            queryBodyLog.setPayload(servletResponseWrapper.getBodyPayload(servlet));
        return queryBodyLog;
    }
}