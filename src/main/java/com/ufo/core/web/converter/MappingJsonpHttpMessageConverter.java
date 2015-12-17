package com.ufo.core.web.converter;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

public class MappingJsonpHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException,
            HttpMessageNotWritableException {

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attr.getRequest();
        String jsonpcallback = ServletRequestUtils.getStringParameter(request, "sjsonp", null);
        if (StringUtils.isNotBlank(jsonpcallback)) {
            outputMessage.getHeaders().setContentType(MediaType.parseMediaType("text/javascript"));
            OutputStream out = outputMessage.getBody();
            out.write(jsonpcallback.getBytes());
            out.write("(".getBytes());
            super.writeInternal(o, outputMessage);
            out.write(")".getBytes());
            out.flush();
        } else {
            super.writeInternal(o, outputMessage);
        }
    }

}
