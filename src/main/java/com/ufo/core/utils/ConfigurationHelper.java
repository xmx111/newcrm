package com.ufo.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

import com.ufo.core.common.ValidationException;

public class ConfigurationHelper {
    private static Integer httpMaxTotalConnections = 100;
    private static Integer httpMaxConnectionsPerRoute = 60;
    private static Integer httpConnectionTimeout = 20000;
    private static Integer httpSoTimeout = 300000;
    private static boolean httpProxySet = false;
    private static String httpProxyHostname = "";
    private static Integer httpProxyPort = 0;

    private static final String CONFIGURATION_FILE = "http.properties";
    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ConfigurationHelper.class);
    private static Properties props = new Properties();
    private static Object locker = new Object();

    static {
        // find configuration file
        InputStream is = ConfigurationHelper.class.getClassLoader().getResourceAsStream(CONFIGURATION_FILE);
        // load properties
        try {
            if (is == null) {
                logger.debug("Can not find configuration file " + CONFIGURATION_FILE);
            } else {
                props.load(is);
            }
            httpMaxTotalConnections = NumberUtils.toInt(props.getProperty("http.maxTotalConnections"),httpMaxTotalConnections);
            httpMaxConnectionsPerRoute = NumberUtils.toInt(props.getProperty("http.maxConnectionsPerRoute"),httpMaxConnectionsPerRoute);
            httpConnectionTimeout = NumberUtils.toInt(props.getProperty("http.connectionTimeout"),httpConnectionTimeout);
            httpSoTimeout = NumberUtils.toInt(props.getProperty("http.soTimeout"), httpSoTimeout);
            httpProxySet = Boolean.getBoolean(props.getProperty("http.proxy.set"));
            httpProxyHostname = props.getProperty("http.proxy.hostname");
            httpProxyPort = NumberUtils.toInt(props.getProperty("http.proxy.port"), httpProxyPort);
        } catch (IOException e) {
            throw new ValidationException("Read configuration file " + CONFIGURATION_FILE + " failed");
        } finally {
            synchronized (locker) {
                locker.notifyAll();
                locker = null;
            }
        }
    }

    public static Integer getHttpMaxTotalConnections() {
        return httpMaxTotalConnections;
    }

    public static void setHttpMaxTotalConnections(Integer httpMaxTotalConnections) {
        ConfigurationHelper.httpMaxTotalConnections = httpMaxTotalConnections;
    }

    public static Integer getHttpMaxConnectionsPerRoute() {
        return httpMaxConnectionsPerRoute;
    }

    public static void setHttpMaxConnectionsPerRoute(Integer httpMaxConnectionsPerRoute) {
        ConfigurationHelper.httpMaxConnectionsPerRoute = httpMaxConnectionsPerRoute;
    }

    public static Integer getHttpConnectionTimeout() {
        return httpConnectionTimeout;
    }

    public static void setHttpConnectionTimeout(Integer httpConnectionTimeout) {
        ConfigurationHelper.httpConnectionTimeout = httpConnectionTimeout;
    }

    public static Integer getHttpSoTimeout() {
        return httpSoTimeout;
    }

    public static void setHttpSoTimeout(Integer httpSoTimeout) {
        ConfigurationHelper.httpSoTimeout = httpSoTimeout;
    }

    public static boolean isHttpProxySet() {
        return httpProxySet;
    }

    public static void setHttpProxySet(boolean httpProxySet) {
        ConfigurationHelper.httpProxySet = httpProxySet;
    }

    public static String getHttpProxyHostname() {
        return httpProxyHostname;
    }

    public static void setHttpProxyHostname(String httpProxyHostname) {
        ConfigurationHelper.httpProxyHostname = httpProxyHostname;
    }

    public static Integer getHttpProxyPort() {
        return httpProxyPort;
    }

    public static void setHttpProxyPort(Integer httpProxyPort) {
        ConfigurationHelper.httpProxyPort = httpProxyPort;
    }

}
