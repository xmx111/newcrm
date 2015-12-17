package com.ufo.core.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

public final class SecurityContext {
    /** 
    *urlMap key url value authority
    */
    private Map<String, Set<String>> urlMap = null;
    private Map<String, String> descMap = null;
    private static SecurityContext instance;

    private SecurityContext() {
        urlMap = new HashMap<String, Set<String>>();
        descMap = new HashMap<String, String>();
    }

    public static SecurityContext instance() {
        if (instance == null) {
            instance = new SecurityContext();
        }
        return instance;
    }

    public void put(String url, String authority) {
        if (StringUtils.isBlank(url) || StringUtils.isBlank(authority)) {
            return;
        }
        if (!url.startsWith("/")) {
            url = "/" + url;
        }
        if (!urlMap.containsKey(url)) {
            urlMap.put(url, new HashSet<String>());
        }
        urlMap.get(url).add(authority);
    }

    public void put(String url, Collection<String> authorities) {
        if (StringUtils.isBlank(url) || CollectionUtils.isEmpty(authorities)) {
            return;
        }
        for (String authority : authorities) {
            put(url, authority);
        }
    }

    public Set<String> getUrls() {
        return urlMap.keySet();
    }

    public Set<String> get(String url) {
        return urlMap.get(url);
    }

    /** 
     * 获取所有权限
    * @return
    */
    public Set<String> getAuthorities() {
        Set<String> result = new HashSet<String>();
        Collection<Set<String>> values = urlMap.values();
        for (Set<String> set : values) {
            result.addAll(set);
        }
        return result;
    }

    //权限编码
    public List<String> getAuthoritiesCode() {
        Set<String> keySet = descMap.keySet();
        Set<String> keys = new HashSet<String>(5);
        for (String key : keySet) {
            if (StringUtils.isBlank(key)) {
                continue;
            }
            int idx = 0;
            do {
                int pos = key.indexOf(".", idx);
                String code = pos == -1 ? key : key.substring(0, pos);
                idx = pos == -1 ? key.length() : pos + 1;
                if (!keys.contains(code)) {
                    keys.add(code);
                }
            } while (idx < key.length());

        }
        return new ArrayList<String>(keys);
    }

    public void putDesc(String code, String name) {
        descMap.put(code, name);
    }

    public String getDesc(String code) {
        return descMap.get(code);
    }

}
