package com.kse.spring.log.library.wrapper;

import com.kse.spring.log.library.util.StreamHttpServlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public interface ServletWrapper {

    String getBodyPayload(StreamHttpServlet servlet) throws IOException;

    default Map<String, String> extractHeaderEnumeration(StreamHttpServlet servlet, Enumeration<String> headerNames) {
        Map<String, String> header = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = getHeaderValueByKey(servlet, key);
            header.put(key, value);
        }
        return header;
    }

    String getHeaderValueByKey(StreamHttpServlet servlet, String key);
}