package com.ufo.core.utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.setSerializationInclusion(Include.NON_EMPTY);
    }

    public static String toJson(Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return "{error:\"" + e.getMessage() + "\"}";
        }
    }

    public static <T> T fromUrl(URL src, Class<T> valueType) {
        try {
            return mapper.readValue(src, valueType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /** 
     * 
    * @param src url
    * @param ref  TypeReference ref = new TypeReference<List<Integer>>() { };
    * @return
    */
    public static <T> T fromUrl(URL src, TypeReference<T> ref) {
        try {
            return mapper.readValue(src, ref);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    public static <T> T fromString(String content, Class<T> valueType) {
        try {
            return mapper.readValue(content, valueType);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /** 
    * @param content
    * @param ref   TypeReference ref = new TypeReference<List<Integer>>() { };
    * @return
    * @return
    */
    public static <T> T fromString(String content, TypeReference<T> ref) {
        try {
            return mapper.readValue(content, ref);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        String url = "http://m.weather.com.cn/data/101250101.html";
        try {
            Map map = fromUrl(new URL(url), Map.class);
            System.out.println(map);
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
        }
    }

}
