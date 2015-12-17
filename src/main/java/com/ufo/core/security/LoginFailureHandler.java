package com.ufo.core.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.ufo.core.context.RuntimeContextBuilder;
import com.ufo.core.context.RuntimeContextThreadLocal;
import com.ufo.core.utils.LogUtils;

/** 
* 类名称：LoginHandler 
* 类描述：登陆日志记录 
* 
* 创建人：Duzj
* 创建时间：2012-12-6 下午3:48:38 
* @version 
*/
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("login failure " + exception);
        }
        RuntimeContextBuilder.build(request);
        LogUtils.addLogin(exception.getLocalizedMessage(), false);
        RuntimeContextThreadLocal.clear();
        super.onAuthenticationFailure(request, response, exception);
    }

}
