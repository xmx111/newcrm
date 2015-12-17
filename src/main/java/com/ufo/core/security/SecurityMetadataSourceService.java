package com.ufo.core.security;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ufo.core.annotation.Description;

public class SecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource, ApplicationContextAware {

    private ApplicationContext applicationContext;
    private PathMatcher pathMatcher = new AntPathMatcher();
    private static Map<String, Collection<ConfigAttribute>> cache;

    public SecurityMetadataSourceService() {
        cache = new HashMap<String, Collection<ConfigAttribute>>();
    }

    private void init() {
        if (applicationContext == null) {
            return;
        }
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(Controller.class);
        Set<String> beanNames = map.keySet();
        for (String beanName : beanNames) {
            Class<?> handlerType = applicationContext.getType(beanName);
            Set<Class<?>> handlerTypes = new LinkedHashSet<Class<?>>();
            RequestMapping mapping = AnnotationUtils.findAnnotation(handlerType, RequestMapping.class);
            if (null == mapping) {
                continue;
            }
            final SecurityContext context = SecurityContext.instance();
            Description desc = AnnotationUtils.findAnnotation(handlerType, Description.class);
            Secured secured = AnnotationUtils.findAnnotation(handlerType, Secured.class);
            initSecuredDescription(desc, secured);
            final String[] urls = mapping.value();
            handlerTypes.add(handlerType);
            handlerTypes.addAll(Arrays.asList(handlerType.getInterfaces()));
            for (Class<?> currentHandlerType : handlerTypes) {
                ReflectionUtils.doWithMethods(currentHandlerType, new ReflectionUtils.MethodCallback() {
                    public void doWith(Method method) {
                        Description description = AnnotationUtils.findAnnotation(method, Description.class);
                        Secured _secured = AnnotationUtils.findAnnotation(method, Secured.class);
                        //方法权限说明
                        initSecuredDescription(description, _secured);
                        if (_secured == null) {
                            return;
                        }
                        String[] value = _secured.value();
                        if (null == value || value.length == 0) {
                            return;
                        }
                        Collection<String> coll = new HashSet<String>(Arrays.asList(value));
                        RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
                        if (mapping != null) {
                            String[] mappedPatterns = mapping.value();
                            if (mappedPatterns.length > 0) {
                                for (String mappedPattern : mappedPatterns) {
                                    String url = mappedPattern;
                                    if (!mappedPattern.startsWith("/")) {
                                        url = "/" + mappedPattern;
                                    }
                                    if (urls != null && urls.length != 0) {
                                        for (String root : urls) {
                                            url = root + url;
                                            url = url.replaceAll("//", "/");
                                            context.put(url, coll);
                                        }
                                    } else {
                                        context.put(url, coll);
                                    }

                                }
                            }
                        }
                    }
                }, ReflectionUtils.USER_DECLARED_METHODS);
            }
        }
    }

    /** 
     * 初始化权限名称 
    * @param desc
    * @param secured
    */
    private void initSecuredDescription(Description desc, Secured secured) {
        if (null == desc) {
            return;
        }
        final SecurityContext context = SecurityContext.instance();
        String[] codes = desc.code();
        if (codes.length == 1 && StringUtils.isBlank(codes[0]) && null != secured) {
            codes = secured.value();
        }
        String[] names = desc.value();
        for (int i = 0; i < codes.length; i++) {
            if (i >= names.length) {
                break;
            }
            context.putDesc(codes[i], names[i]);
        }

    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        //使用缓存
        if (cache.containsKey(url)) {
            return cache.get(url);
        }
        Set<String> set = SecurityContext.instance().getUrls();
        for (String resURL : set) {
            if (pathMatcher.match(url, resURL)) {
                Set<String> authorities = SecurityContext.instance().get(resURL);
                Collection<ConfigAttribute> coll = new HashSet<ConfigAttribute>();
                for (String authority : authorities) {
                    coll.add(new SecurityConfig(authority));
                }
                cache.put(url, coll);
                return coll;
            }
        }
        return null;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        Set<String> authorities = SecurityContext.instance().getAuthorities();
        List<ConfigAttribute> result = new ArrayList<ConfigAttribute>(authorities.size());
        for (String code : authorities) {
            result.add(new SecurityConfig(code));
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
        init();
    }

}
