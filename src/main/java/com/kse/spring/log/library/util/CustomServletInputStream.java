package com.kse.spring.log.library.util;

import lombok.Data;
import lombok.NonNull;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

@Data
public class CustomServletInputStream extends ServletInputStream {

    private InputStream inputStream;

    private ServletInputStream servletInputStream = new ServletInputStream() {

        boolean isFinished = false;
        boolean isReady = true;
        ReadListener readListener = null;

        @Override
        public int read() throws IOException {
            int i = inputStream.read();
            isFinished = i == -1;
            isReady = !isFinished;
            return i;
        }

        @Override
        public boolean isFinished() {
            return isFinished;
        }

        @Override
        public boolean isReady() {
            return isReady;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            this.readListener = readListener;
        }
    };

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
    }

    @Override
    public synchronized void mark(int readlimit) {
        inputStream.mark(readlimit);
    }

    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }

    @Override
    public int read(@NonNull byte[] b, int off, int len) throws IOException {
        return inputStream.read(b, off, len);
    }

    @Override
    public synchronized void reset() throws IOException {
        inputStream.reset();
    }

    @Override
    public long skip(long n) throws IOException {
        return inputStream.skip(n);
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public void setReadListener(ReadListener readListener) {
        servletInputStream.setReadListener(readListener);
    }

    public boolean isReady() {
        return servletInputStream.isReady();
    }

    @Override
    public boolean isFinished() {
        return servletInputStream.isFinished();
    }
}