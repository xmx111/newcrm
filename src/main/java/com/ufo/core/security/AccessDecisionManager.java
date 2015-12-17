package com.ufo.core.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class AccessDecisionManager implements org.springframework.security.access.AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        if (configAttributes == null) {
            return;
        }
        final SecurityContext context = SecurityContext.instance();
        Iterator<ConfigAttribute> ite = configAttributes.iterator();
        Set<String> set = new HashSet<String>();
        while (ite.hasNext()) {
            ConfigAttribute ca = ite.next();
            String code = ((SecurityConfig) ca).getAttribute();
            String desc = context.getDesc(code);
            set.add(StringUtils.isNotBlank(desc) ? desc : code);
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                if (code.trim().equals(ga.getAuthority().trim())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(ArrayUtils.toString(set));
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return true;

    }

}
