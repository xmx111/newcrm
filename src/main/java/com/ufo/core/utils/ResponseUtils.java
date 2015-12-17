package com.ufo.core.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtils {

    public static void write(HttpServletResponse response, Object result) throws IOException {
        response.setHeader("Content-Type", "application/json");
        String charset = "utf-8";
        response.setCharacterEncoding(charset);
        response.setCharacterEncoding(charset);
        final ServletOutputStream outputStream = response.getOutputStream();
        final String json = JsonUtils.toJson(result);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream, charset);
        writer.write(json);
        writer.flush();
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
