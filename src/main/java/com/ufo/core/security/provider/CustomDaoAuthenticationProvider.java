package com.ufo.core.security.provider;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.ufo.config.sys.entity.Manager;
import com.ufo.core.common.ValidationException;
import com.ufo.core.security.repository.TokenRepository;
import com.ufo.core.utils.HttpClientUtils;

public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {
    
    private String ssoLoginUrl;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (StringUtils.isBlank(ssoLoginUrl))
            super.additionalAuthenticationChecks(userDetails, authentication);
        else {
            try {
                Map<String, String> params = new HashMap<String, String>();
                params.put("loginName", ((Manager)userDetails).getLoginName());
                params.put("password", userDetails.getPassword());
                // 单点登录检验用户
                String resp = HttpClientUtils.post(ssoLoginUrl, params);
                if (StringUtils.isBlank(resp))
                    throw new BadCredentialsException("密码出错!");
                TokenRepository.getTokenMap().put(params.get("loginName"), resp);
            } catch(ValidationException e){
                // 连接不到服务器则内部验证
                super.additionalAuthenticationChecks(userDetails, authentication);
            }
        }
    }

    public String getSsoLoginUrl() {
        return ssoLoginUrl;
    }

    public void setSsoLoginUrl(String ssoLoginUrl) {
        this.ssoLoginUrl = ssoLoginUrl;
    }
}
