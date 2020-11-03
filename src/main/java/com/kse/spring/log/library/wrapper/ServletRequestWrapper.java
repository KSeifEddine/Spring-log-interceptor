package com.kse.spring.log.library.wrapper;

import com.kse.spring.log.library.util.StreamHttpServlet;
import com.kse.spring.log.library.util.StreamHttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ServletRequestWrapper implements ServletWrapper {

    @Override
    public String getBodyPayload(StreamHttpServlet servlet) throws IOException {
        return IOUtils.toString(((StreamHttpServletRequest) servlet).getReader());
    }

    public Map<String, String> getHeaderAttributes(StreamHttpServlet servlet) {
        Enumeration<String> headerNames = ((StreamHttpServletRequest) servlet).getHeaderNames();
        return extractHeaderEnumeration(servlet, headerNames);
    }

    public String getHeaderValueByKey(StreamHttpServlet servlet, String key) {
        return ((HttpServletRequest) servlet).getHeader(key);
    }

    public String getRemoteHostFromRequest(HttpServletRequest request) {
        String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
        if ((ipFromHeader != null) && (ipFromHeader.length() > 0)) {
            return ipFromHeader;
        }
        return request.getRemoteAddr();
    }

    public Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> enumeration = request.getParameterNames();
        while (Objects.requireNonNull(enumeration).hasMoreElements()) {
            String param = enumeration.nextElement();
            params.put(param, request.getParameter(param));
        }
        return params;
    }
}