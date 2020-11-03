package com.kse.spring.log.library.util;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.IOException;
import java.io.OutputStream;

public class CustomServletOutputStream extends ServletOutputStream {

    private OutputStream outputStream;
    private StreamHttpServletResponse streamHttpServletResponse;

    CustomServletOutputStream(StreamHttpServletResponse streamHttpServletResponse) throws  IOException {
        this.outputStream = streamHttpServletResponse.getResponse().getOutputStream();
        this.streamHttpServletResponse = streamHttpServletResponse;
    }

    private ServletOutputStream servletOutputStream = new ServletOutputStream() {
        boolean isReady = true;
        WriteListener writeListener = null;

        @Override
        public boolean isReady() {
            return isReady;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            this.writeListener = writeListener;
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
            streamHttpServletResponse.getRawData().add(((Integer) b).byteValue());
        }
    };

    @Override
    public void close() throws IOException {
        outputStream.close();
    }

    @Override
    public void setWriteListener(WriteListener writeListener) {
        servletOutputStream.setWriteListener(writeListener);
    }

    @Override
    public boolean isReady() {
        return servletOutputStream.isReady();
    }

    @Override
    public void write(int b) throws IOException {
        servletOutputStream.write(b);
    }
}