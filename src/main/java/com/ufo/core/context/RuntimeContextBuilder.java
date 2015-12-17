package com.ufo.core.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.support.RequestContextUtils;

import com.ufo.core.utils.RequestUtils;
import com.ufo.core.utils.SecurityUtils;

public class RuntimeContextBuilder {
    /**
     * Build http type of runtime context from http request
     * @param request
     */
    public static void build(HttpServletRequest request) {
        RuntimeContext runtimeContext = RuntimeContextThreadLocal.create();
        runtimeContext.setProtocol("HTTP");
        runtimeContext.setClientIp(RequestUtils.getIpAddr(request));
        runtimeContext.setRequestUrl(request.getRequestURL().toString());
        HttpSession session = request.getSession(false);
        if (session != null) {
            runtimeContext.setSessionId(session.getId());
        }
        runtimeContext.setLocale(RequestContextUtils.getLocale(request));
        runtimeContext.setUser(SecurityUtils.currentUser());

        String queryString = request.getQueryString();
        runtimeContext.setQueryString(queryString);

        StringBuffer uri = new StringBuffer(request.getRequestURI());
        uri.delete(0, request.getContextPath().length());
        runtimeContext.setRequestUri(uri.toString());
        // User-Agent 标准格式为： 浏览器标识 (操作系统标识; 加密等级标识; 浏览器语言) 渲染引擎标识 版本信息 
        //浏览器版本
        String agent = request.getHeader("User-Agent");
        runtimeContext.setUserAgent(agent);
    }

    /**
     * Build socket type of runtime context
     * @param clientIp
     * @param clientPort
     * @param requestUrl we can use this url to store the request type (from business aspect)
     */
    public static void build(String clientIp, int clientPort, String requestUrl) {
        RuntimeContext runtimeContext = RuntimeContextThreadLocal.create();
        runtimeContext.setProtocol("SOCKET");
        runtimeContext.setClientIp(clientIp + ":" + clientPort);
        runtimeContext.setRequestUrl(requestUrl);
        runtimeContext.setUser(SecurityUtils.currentUser());
    }
}
