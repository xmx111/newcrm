package com.ufo.core.security;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.ufo.core.common.ProcResult;
import com.ufo.core.utils.ResponseUtils;

@SuppressWarnings("deprecation")
public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        String requestType = request.getHeader("X-Requested-With");
        String url = request.getRequestURI();
        final String contextPath = request.getContextPath();
        if (StringUtils.isBlank(requestType) && url.endsWith(".htm")) {
            String param = "?";
            Enumeration<String> names = request.getParameterNames();
            while (names.hasMoreElements()){
                String name = names.nextElement();
                param += name + "=" + request.getParameter(name);
            }
            String path = request.getRequestURI().replaceFirst(contextPath, "");
            response.sendRedirect(contextPath + "/login.htm?redirect=" + path + param);
        } else {
            ProcResult result = new ProcResult();
            result.setSmessage("会话超时,请重新登陆!");
            result.setSstatuscode(301);
            result.setType("failure");
            ResponseUtils.write(response, result);
        }
    }

}
