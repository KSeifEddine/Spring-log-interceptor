package com.kse.spring.log.library.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class StreamHttpServletResponse extends HttpServletResponseWrapper implements StreamHttpServlet {

    String invokeId;
    private List<Byte> rawData = new ArrayList<>();
    private HttpServletResponse response;
    private CustomServletOutputStream servletOutputStream;

    public StreamHttpServletResponse(HttpServletResponse response) throws IOException {
        super(response);
        this.response = response;
        this.servletOutputStream = new CustomServletOutputStream(this);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return servletOutputStream;
    }

    public PrintWriter getWriter() throws IOException {
        String encoding = getCharacterEncoding();
        if (encoding != null) {
            return new PrintWriter(new OutputStreamWriter(servletOutputStream, encoding));
        } else {
            return new PrintWriter(new OutputStreamWriter(servletOutputStream));
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

    public List<Byte> getRawData() {
        return rawData;
    }

    @Override
    public HttpServletResponse getResponse() {
        return response;
    }
}