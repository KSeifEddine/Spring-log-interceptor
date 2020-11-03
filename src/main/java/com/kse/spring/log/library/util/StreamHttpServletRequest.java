package com.kse.spring.log.library.util;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class StreamHttpServletRequest extends HttpServletRequestWrapper implements StreamHttpServlet {

    private byte[] rawData = {};
    private HttpServletRequest request;
    private CustomServletInputStream servletStream;
    private String invokeId;

    public StreamHttpServletRequest(HttpServletRequest request) {
        super(request);
        this.servletStream = new CustomServletInputStream();
        this.request = request;
        invokeId = request.getMethod() + request.getRequestURI() + Timestamp.valueOf(LocalDateTime.now()).toString();
    }

    private void initRawData() throws IOException {
        if (rawData.length == 0) {
            byte[] b = IOUtils.toByteArray(this.request.getInputStream());
            if (b != null)
                rawData = b;
        }
        servletStream.setInputStream(new ByteArrayInputStream(rawData));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        initRawData();
        return servletStream;
    }

    public BufferedReader getReader() throws IOException {
        initRawData();
        String encoding = getCharacterEncoding();
        if (encoding != null) {
            return new BufferedReader(new InputStreamReader(servletStream, encoding));
        } else {
            return new BufferedReader(new InputStreamReader(servletStream));
        }
    }

    @Override
    public void setInvokeId(String invokeId) {
        this.invokeId = invokeId;
    }

    @Override
    public String getInvokeId() {
        return this.invokeId;
    }
}