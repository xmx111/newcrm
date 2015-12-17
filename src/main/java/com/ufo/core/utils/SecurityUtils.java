package com.ufo.core.utils;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.ufo.core.security.SecurityContext;

public final class SecurityUtils {

    /** 
     *获取当前操作数据
    * @return
    */
    public static UserDetails currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object object = authentication == null ? null : authentication.getPrincipal();
        return object instanceof UserDetails ? (UserDetails) object : null;
    }

    /** 
     * 当前登陆用户是否有权限访问指定的URL
    * @param url
    * @return
    */
    public static boolean hasAuthorize(String url) {
        if (StringUtils.isBlank(url)) {
            return true;
        }
        UserDetails oper = currentUser();
        if (null == oper) {
            return false;
        }
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        if (url.indexOf("?") != -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        //指定的URL无权限控制
        Set<String> set = SecurityContext.instance().get(url);
        if (CollectionUtils.isEmpty(set)) {
            return true;
        }
        //判断是否授权
        Collection<? extends GrantedAuthority> authorities = oper.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            if (set.contains(authority.getAuthority())) {
                return true;
            }
        }
        return false;
    }

    /** 
     * 当前登陆用户是否具有所有指定权限
    * @param url
    * @return
    */
    public static boolean hasAllAuthorize(String... codes) {
        if (null == codes || codes.length == 0) {
            return true;
        }
        UserDetails oper = currentUser();
        if (null == oper) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = oper.getAuthorities();
        int cnt = 0;
        for (String code : codes) {
            for (GrantedAuthority au : authorities) {
                if (au.getAuthority().equals(code)) {
                    cnt += 1;
                    break;
                }
            }
        }
        return cnt == codes.length;
    }

    /** 
     * 当前登陆用户是否具有任一指定权限
    * @param url
    * @return
    */
    public static boolean hasAnyAuthorize(String... codes) {
        if (null == codes || codes.length == 0) {
            return true;
        }
        UserDetails oper = currentUser();
        if (null == oper) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = oper.getAuthorities();
        int cnt = 0;
        for (String code : codes) {
            for (GrantedAuthority au : authorities) {
                if (au.getAuthority().equals(code)) {
                    cnt += 1;
                    break;
                }
            }
        }
        return cnt != 0;
    }

    /** 
     * 当前登陆用户是否具有任一指定权限
    * @param url
    * @return
    */
    public static boolean hasNotAuthorize(String... codes) {
        if (null == codes || codes.length == 0) {
            return true;
        }
        UserDetails oper = currentUser();
        if (null == oper) {
            return false;
        }
        Collection<? extends GrantedAuthority> authorities = oper.getAuthorities();
        int cnt = 0;
        for (String code : codes) {
            for (GrantedAuthority au : authorities) {
                if (au.getAuthority().equals(code)) {
                    cnt += 1;
                    break;
                }
            }
        }
        return cnt == 0;
    }
}
