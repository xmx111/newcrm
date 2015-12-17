package com.ufo.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.ufo.core.utils.ResponseUtils;

public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
        String requestType = request.getHeader("X-Requested-With");
        String url = request.getRequestURI();
        final String contextPath = request.getContextPath();
        if (StringUtils.isBlank(requestType) && url.endsWith(".htm")) {
            String path = request.getRequestURI();
            request.getSession().setAttribute("message", accessDeniedException.getMessage());
            request.getSession().setAttribute("redirect", path);
            response.sendRedirect(contextPath + "/accessDenied.htm");
        } else {
            String result = "<script language='javascript'>Global.notify('error', '请添加以下权限:" + accessDeniedException.getMessage() + "');</script>";
            ResponseUtils.write(response, result);
        }
    }
}
