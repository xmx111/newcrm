package com.ufo.core.context;

import java.util.Locale;

public class RuntimeContext {
    private String clientIp;
    private String protocol;
    private String requestUrl;
    private String requestUri;
    private String queryString;
    private String sessionId;
    private String userAgent;
    private Locale locale;
    private Object user;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * @return the clientIp
     */
    public String getClientIp() {
        return clientIp;
    }

    /**
     * @param clientIp the clientIp to set
     */
    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }

    /**
     * @return the requestUrl
     */
    public String getRequestUrl() {
        return requestUrl;
    }

    /**
     * @param requestUrl the requestUrl to set
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @param locale the locale to set
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * @return the user
     */
    @SuppressWarnings("unchecked")
    public <T> T getUser() {
        return (T) user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Object user) {
        this.user = user;
    }

    @Override
    public String toString() {
        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("{clientIp:").append(clientIp);
        msgBuilder.append(", sessionId:").append(sessionId);
        msgBuilder.append(", requestUrl:").append(requestUrl);
        msgBuilder.append(", requestUri:").append(requestUri);
        msgBuilder.append(", queryString:").append(queryString);
        msgBuilder.append(", userAgent:").append(userAgent);
        msgBuilder.append(", user:").append(user).append("}");
        return msgBuilder.toString();
    }
}
