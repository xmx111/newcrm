package com.ufo.core.cache;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EnumsCache {
    
    private static EnumsCache enumsCache;
    
    private Map<String, Map<? extends Serializable, String>> enumsMap;
    
    private EnumsCache(){
        
    }
    
    public static EnumsCache getTokenRepository(){
        if (enumsCache == null){
            enumsCache = new EnumsCache();
            enumsCache.enumsMap = new ConcurrentHashMap<String, Map<? extends Serializable, String>>();
        }
        return enumsCache;
    }
    
    public Map<String, Map<? extends Serializable, String>> getAll(){
        return enumsMap;
    }
    
    public void put(String name, Map<? extends Serializable, String> map){
        enumsMap.put(name, map);
    }
    
    public Set<Entry<String, Map<? extends Serializable, String>>> entrySet(){
        return enumsMap.entrySet();
    }
    
    public Map<? extends Serializable, String> getEnums(String name){
        return enumsMap.get(name);
    }
}
