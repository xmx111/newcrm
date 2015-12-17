package com.ufo.core.security;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ufo.core.common.ValidationException;
import com.ufo.core.utils.HttpClientUtils;

@SuppressWarnings("deprecation")
@Service("tokenRememberMeServices")
@Transactional
public class TokenRememberMeServices extends PersistentTokenBasedRememberMeServices{
    
    private String ssoTokenUrl;

    private static final String DELIMITER = ":";
    
    @Override
    protected UserDetails processAutoLoginCookie(String[] cookieTokens, HttpServletRequest request, HttpServletResponse response) {
        
        if (StringUtils.isEmpty(ssoTokenUrl))
            throw new RememberMeAuthenticationException("没有设置单点登录url");
        
        PersistentRememberMeToken token = getPersistentRememberMeToken(request);

        Map<String, String> params = new HashMap<String, String>();
        params.put("loginName", token.getUsername());
        params.put("token", token.getSeries());
        try{
            // 检验token
            String resp = HttpClientUtils.post(ssoTokenUrl, params);
            
            if (StringUtils.isEmpty(resp) || !resp.equals(token.getSeries())) {
                throw new RememberMeAuthenticationException("token不存在: " + token.getSeries());
            }
        } catch(ValidationException e){
            // 连接不到服务器
            throw new RememberMeAuthenticationException(e.getMessage());
        }

        // Token also matches, so login is valid. Update the token value, keeping the *same* series number.
        if (logger.isDebugEnabled()) {
            logger.debug("Refreshing persistent login token for user '" + token.getUsername() + "', series '" +
                    token.getSeries() + "'");
        }

        return getUserDetailsService().loadUserByUsername(token.getUsername());
    }
    
    private PersistentRememberMeToken getPersistentRememberMeToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if ((cookies == null) || (cookies.length == 0)) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (this.getCookieName().equals(cookie.getName())) {
                String[] cookieTokens = this.decodeCookie(cookie.getValue());
                return new PersistentRememberMeToken(cookieTokens[0], cookieTokens[1], null, null);
            }
        }
        return null;
    }
    
    @Override
    protected String[] decodeCookie(String cookieValue) throws InvalidCookieException {
        for (int j = 0; j < cookieValue.length() % 4; j++) {
            cookieValue = cookieValue + "=";
        }

        if (!Base64.isBase64(cookieValue.getBytes())) {
            throw new InvalidCookieException( "Cookie token was not Base64 encoded; value was '" + cookieValue + "'");
        }

        String cookieAsPlainText = new String(Base64.decode(cookieValue.getBytes()));

        String[] tokens = StringUtils.delimitedListToStringArray(cookieAsPlainText, DELIMITER);

        if ((tokens[0].equalsIgnoreCase("http") || tokens[0].equalsIgnoreCase("https")) && tokens[1].startsWith("//")) {
            // Assume we've accidentally split a URL (OpenID identifier)
            String[] newTokens = new String[tokens.length - 1];
            newTokens[0] = tokens[0] + ":" + tokens[1];
            System.arraycopy(tokens, 2, newTokens, 1, newTokens.length - 1);
            tokens = newTokens;
        }

        return tokens;
    }

    public String getSsoTokenUrl() {
        return ssoTokenUrl;
    }

    public void setSsoTokenUrl(String ssoTokenUrl) {
        this.ssoTokenUrl = ssoTokenUrl;
    }
}
