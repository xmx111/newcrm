package com.ufo.core.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

import com.ufo.config.sys.entity.Manager;
import com.ufo.core.context.RuntimeContextBuilder;
import com.ufo.core.context.RuntimeContextThreadLocal;
import com.ufo.core.security.repository.TokenRepository;
import com.ufo.core.utils.LogUtils;

/** 
* 类名称：LoginHandler 
* 类描述：登陆日志记录 
* 
* 创建人：Duzj
* 创建时间：2012-12-6 下午3:48:38 
* @version 
*/
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    
    private static final String DELIMITER = ":";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        if (logger.isDebugEnabled()) {
            logger.debug("login sucess " + authentication);
        }
        
        // 记住则加cookie
        if (rememberMeRequested(request, "_spring_security_remember_me")){
            String username = ((Manager) authentication.getPrincipal()).getLoginName();
    
            logger.debug("Creating new persistent login for user " + username);
    
            PersistentRememberMeToken persistentToken = new PersistentRememberMeToken(username, TokenRepository.getTokenMap().get(username), null, new Date());
            try {
                // token放入内存
                TokenRepository.getTokenRepository().createNewToken(persistentToken);
                addCookie(persistentToken, request, response);
            } catch (DataAccessException e) {
                logger.error("Failed to save persistent token ", e);
            }
        }
        
        RuntimeContextBuilder.build(request);
        LogUtils.addLogin(null, true);
        RuntimeContextThreadLocal.clear();
        super.onAuthenticationSuccess(request, response, authentication);
    }
    
    private void addCookie(PersistentRememberMeToken token, HttpServletRequest request, HttpServletResponse response) {
        setCookie(new String[] {token.getUsername(), token.getSeries()}, AbstractRememberMeServices.TWO_WEEKS_S, request, response);
    }

    protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request, HttpServletResponse response) {
        String cookieValue = encodeCookie(tokens);
        Cookie cookie = new Cookie(TokenRepository.cookieName, cookieValue);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getCookiePath(request));

        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }
    
    protected String encodeCookie(String[] cookieTokens) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < cookieTokens.length; i++) {
            sb.append(cookieTokens[i]);

            if (i < cookieTokens.length - 1) {
                sb.append(DELIMITER);
            }
        }

        String value = sb.toString();

        sb = new StringBuilder(new String(Base64.encode(value.getBytes())));

        while (sb.charAt(sb.length() - 1) == '=') {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    protected boolean rememberMeRequested(HttpServletRequest request, String parameter) {

        String paramValue = request.getParameter(parameter);

        if (paramValue != null) {
            if (paramValue.equalsIgnoreCase("true") || paramValue.equalsIgnoreCase("on") ||
                    paramValue.equalsIgnoreCase("yes") || paramValue.equals("1")) {
                return true;
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Did not send remember-me cookie (principal did not set parameter '" + parameter + "')");
        }

        return false;
    }
}
