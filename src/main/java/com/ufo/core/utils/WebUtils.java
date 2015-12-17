package com.ufo.core.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ufo.core.dto.TreeDTO;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class WebUtils {
    public static <T extends TreeDTO> String toTree(List<T> list, String url, String refId) {
        return toTree(list, url, refId, null);
    }

    /** 
    * @param list
    * @param ctx 
    * @return
    */
    public static <T extends TreeDTO> String toTree(List<T> list, String url, String refId, String target) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String ctx = attr.getRequest().getContextPath();
        url = ctx + (url.startsWith("/") ? "" : "/") + url;
        StringBuffer buf = new StringBuffer();
        target = StringUtils.isEmpty(target) ? "sid_user" : target;
        for (T dto : list) {
            buf.append("<li target=\"#target#\" rel=\"");
            buf.append(dto.getId());
            buf.append("\"> <a ");
            if (StringUtils.isNotBlank(url)) {
                buf.append(" href=\"#url#");
                buf.append(dto.getId());
                buf.append("\" target=\"ajax\" rel=\"#refId#\" ");
            }
            buf.append(" > ");
            buf.append(dto.getName());
            buf.append("</a>");
            subTree(dto.getChildren(), buf, url);
            buf.append("</li>");
        }
        return buf.toString().replaceAll("#target#", target).replaceAll("#url#", url).replaceAll("#refId#", refId);
    }

    private static <T extends TreeDTO> void subTree(List<T> coll, StringBuffer buf, String url) {
        if (CollectionUtils.isNotEmpty(coll)) {
            buf.append("<ul>");
            for (T dto : coll) {
                buf.append("<li target=\"#target#\" rel=\"");
                buf.append(dto.getId());
                buf.append("\"><a ");
                if (StringUtils.isNotBlank(url)) {
                    buf.append(" href=\"#url#");
                    buf.append(dto.getId());
                    buf.append("\" target=\"ajax\" rel=\"#refId#\" ");
                }
                buf.append(" > ");
                buf.append(dto.getName());
                buf.append("</a>");
                subTree(dto.getChildren(), buf, url);
                buf.append("</li>");
            }
            buf.append("</ul>");
        }
    }
    
    /**
     * 设置cookie
     * @param response
     * @param name  cookie名字
     * @param value cookie值
     * @param maxAge cookie生命周期  以秒为单位
     */
    public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        if(maxAge>0)
            cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
    
    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request,String name){
        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
            Cookie cookie = (Cookie)cookieMap.get(name);
            return cookie;
        }else{
            return null;
        }  
    }
    
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){ 
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
}
