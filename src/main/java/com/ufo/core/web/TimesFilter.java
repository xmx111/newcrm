package com.ufo.core.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ufo.core.context.RuntimeContextBuilder;
import com.ufo.core.context.RuntimeContextThreadLocal;

public class TimesFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(TimesFilter.class);
    private static final String TIMES_KEY = TimesFilter.class.getName() + "_times";
    private boolean ignore = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String ignore = filterConfig.getInitParameter("ignore");
        if (ignore != null && ignore.trim().length() != 0) {
            this.ignore = Boolean.valueOf(ignore);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        if (!ignore && request.getAttribute(TIMES_KEY) == null) {
            Long begin = System.currentTimeMillis();
            request.setAttribute(TIMES_KEY, begin);
        }
        //设置运行时的环境变量
        if (null == RuntimeContextThreadLocal.get() && request instanceof HttpServletRequest) {
            RuntimeContextBuilder.build((HttpServletRequest) request);
        }
        chain.doFilter(request, response);
        if (!ignore) {
            Long begin = (Long) request.getAttribute(TIMES_KEY);
            long times = System.currentTimeMillis() - begin;
            StringBuffer url = ((HttpServletRequest) request).getRequestURL();
            logger.info("执行" + url.toString() + "请求耗时:" + times);
        }
        RuntimeContextThreadLocal.clear();
    }

    @Override
    public void destroy() {

    }

}
