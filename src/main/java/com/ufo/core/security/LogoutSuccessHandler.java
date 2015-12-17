package com.ufo.core.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.util.Assert;

import com.ufo.config.sys.entity.Manager;
import com.ufo.core.common.ValidationException;
import com.ufo.core.security.repository.TokenRepository;
import com.ufo.core.utils.HttpClientUtils;

public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
    
//    private String ssoCleanUrl;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        Assert.notNull(request, "HttpServletRequest required");
        String username = authentication==null||authentication.getPrincipal()==null?"":((Manager) authentication.getPrincipal()).getLoginName();
        if (logger.isDebugEnabled()) {
            logger.debug( "Logout of user " + username);
        }
        // 清除cookie
        cancelCookie(request, response);
        
//        try{
//            Map<String, String> params = new HashMap<String, String>();
//            params.put("loginName", username);
//            if (StringUtils.isNotBlank(ssoCleanUrl))
//                HttpClientUtils.post(ssoCleanUrl, params);
//        } catch(ValidationException e){
//        }
        
        response.sendRedirect(request.getContextPath() + "/index.htm");
    }
    
    /**
     * Sets a "cancel cookie" (with maxAge = 0) on the response to disable persistent logins.
     */
    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Cancelling cookie");
        Cookie cookie = new Cookie(TokenRepository.cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));

        response.addCookie(cookie);
    }

    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

//    public String getSsoCleanUrl() {
//        return ssoCleanUrl;
//    }
//
//    public void setSsoCleanUrl(String ssoCleanUrl) {
//        this.ssoCleanUrl = ssoCleanUrl;
//    }
}
