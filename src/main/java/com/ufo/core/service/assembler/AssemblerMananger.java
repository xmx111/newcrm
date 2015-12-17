package com.ufo.core.service.assembler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.ufo.core.service.IAssembler;

public class AssemblerMananger {
    @SuppressWarnings("rawtypes")
    //private static Map<Class, Object> cache = new HashMap<Class, Object>();
    private static ThreadLocal<Map<Class, Object>> local = new ThreadLocal<Map<Class, Object>>();

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> T getAssembler(Class<T> type) {
        Map<Class, Object> cache = local.get();
        if (cache == null) {
            cache = new HashMap<Class, Object>();
            local.set(cache);
        }
        if (!cache.containsKey(type)) {
            cache.put(type, BeanUtils.instantiate(type));
        }
        return (T) cache.get(type);
    }

    @SuppressWarnings("rawtypes")
    public static void cleanCache() {
        Map<Class, Object> cache = local.get();
        if (cache != null) {
            Collection<Object> values = cache.values();
            for (Object obj : values) {
                if (obj instanceof IAssembler) {
                    ((IAssembler) obj).cleanCache();
                }
            }
        }

    }
}
