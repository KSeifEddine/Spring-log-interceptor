package com.kse.spring.log.library.wrapper;

import com.kse.spring.log.library.util.StreamHttpServlet;
import com.kse.spring.log.library.util.StreamHttpServletResponse;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;

@Service
public class ServletResponseWrapper implements ServletWrapper {

    @Override
    public String getBodyPayload(StreamHttpServlet servlet) throws IOException {
        byte[] data = new byte[((StreamHttpServletResponse) servlet).getRawData().size()];
        for (int i = 0 ; i < data.length ; i++) {
            data[i] = ((StreamHttpServletResponse) servlet).getRawData().get(i);
        }
        return new String(data);
    }

    public Map<String, String> getHeaderAttributes(StreamHttpServlet servlet) {
        Enumeration<String> headerNames = Collections.enumeration(((StreamHttpServletResponse) servlet).getHeaderNames());
        return extractHeaderEnumeration(servlet, headerNames);
    }

    public String getHeaderValueByKey(StreamHttpServlet servlet, String key) {
        return ((HttpServletResponse) servlet).getHeader(key);
    }
}