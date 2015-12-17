package com.ufo.core.security.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

public class TokenRepository {
    
    public static String cookieName = "mis_framework";

    // 内存
    private static PersistentTokenRepository tokenRepository = null;
    
    // tokenMap
    private static Map<String, String> tokenMap;
    
    private TokenRepository(){
        
    }
    
    public static PersistentTokenRepository getTokenRepository(){
        if (tokenRepository == null)
            tokenRepository = new InMemoryTokenRepositoryImpl();
        return tokenRepository;
    }
    
    public static Map<String, String> getTokenMap(){
        if (tokenMap == null)
            tokenMap = new ConcurrentHashMap<String, String>();
        return tokenMap;
    }
}
