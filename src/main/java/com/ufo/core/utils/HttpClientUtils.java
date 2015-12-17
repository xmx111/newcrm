package com.ufo.core.utils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.ufo.core.common.ValidationException;

public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HtmlUtils.class);
    private static ClientConnectionManager connectionManager;
    private static HttpParams httpParams = null;
    private static HttpParams httpProxyParams = new BasicHttpParams();

    static {
        httpParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParams, ConfigurationHelper.getHttpConnectionTimeout());
        HttpConnectionParams.setSoTimeout(httpParams, ConfigurationHelper.getHttpSoTimeout());
        String hostName = ConfigurationHelper.getHttpProxyHostname();
        if (ConfigurationHelper.isHttpProxySet() && StringUtils.isNotBlank(hostName)) {
            Integer port = ConfigurationHelper.getHttpProxyPort();
            HttpHost proxy = new HttpHost(hostName, port, "http");
            ConnRouteParams.setDefaultProxy(httpParams, proxy);
            ConnRouteParams.setDefaultProxy(httpProxyParams, proxy);
        }
        PlainSocketFactory factory = PlainSocketFactory.getSocketFactory();
        SchemeRegistry schreg = new SchemeRegistry();
        schreg.register(new Scheme("http", 80, factory));
        schreg.register(new Scheme("https", 443, factory));

        connectionManager = new PoolingClientConnectionManager(schreg);

    }

    /**
     * return new httpClient
     * @return
     */
    public static HttpClient getHttpClient() {
        return new DefaultHttpClient(connectionManager, httpParams);
    }

    public static StringBuffer get(String url) {
        String content = getResponseByGet(url);
        return new StringBuffer(content);
    }

    public static StringBuffer get(String url, String charset) {
        String content = getResponseByGet(url, Charset.forName(charset));
        return new StringBuffer(content);
    }

    /**
     * get response content by get method
     * @param url
     * @return
     * @throws ValidationException
     */
    public static String getResponseByGet(String url) throws ValidationException {
        return getResponseByGet(url, Consts.UTF_8);
    }

    public static String getResponseByGet(String url, Charset charset) throws ValidationException {
        return getResponseByGet(new DefaultHttpClient(connectionManager, httpParams), url, charset);
    }

    /**
     * @param url
     * @return
     * @throws ValidationException
     */
    public static String getResponseByGetAndSingleThread(String url) throws ValidationException {
        return getResponseByGet(new DefaultHttpClient(), url);
    }

    /**
     * get response content by get method
     * @param httpClient
     * @param url
     * @return
     * @throws ValidationException
     */
    private static String getResponseByGet(HttpClient httpClient, String url) throws ValidationException {
        return getResponseByGet(httpClient, url, null);
    }

    private static String getResponseByGet(HttpClient httpClient, String url, Charset charset)
            throws ValidationException {
        if (logger.isDebugEnabled()) {
            logger.debug("get data from " + url);
        }
        HttpGet httpGet = null;
        try {
            httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String output = "";
            if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
                if (null != entity) {
                    output = null != charset ? EntityUtils.toString(entity, charset) : EntityUtils.toString(entity);
                }
            }
            if (null != entity) {
                EntityUtils.consume(entity);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("return date " + output);
            }
            return output;
        } catch (ClientProtocolException e) {
            if (httpGet != null) {
                httpGet.abort();
            }
            throw new ValidationException("Get URL content HTTP error", e);
        } catch (IOException e) {
            if (httpGet != null) {
                httpGet.abort();
            }
            throw new ValidationException("Get URL content IO error", e);
        } finally {
        }
    }

    /**
     * get response by post method
     * @param url
     * @param paramsMap
     * @return
     * @throws ValidationException
     */
    public static String getResponseByPost(String url, Map<String, String> paramsMap) throws ValidationException {
        if (MapUtils.isEmpty(paramsMap)) {
            return getResponseByGet(getHttpClient(), url);
        } else {
            return getResponseByPost(getHttpClient(), url, paramsMap, null, null);
        }
    }

    public static String getResponseByPost(String url, List<NameValuePair> params) throws ValidationException {
        if (CollectionUtils.isEmpty(params)) {
            return getResponseByGet(getHttpClient(), url);
        } else {
            return getResponseByPost(getHttpClient(), url, params, null, null);
        }
    }

    public static String post(String url, Map<String, String> params) throws ValidationException {
        return post(url, params, Consts.UTF_8);
    }

    public static String post(String url, Map<String, String> params, Charset charset) throws ValidationException {
        if (MapUtils.isEmpty(params)) {
            return getResponseByGet(getHttpClient(), url, charset);
        } else {
            return getResponseByPost(getHttpClient(), url, mapToPair(params), null, null, charset);
        }
    }

    /**
     * @param url
     * @param requestXML
     * @param headerMap
     * @return
     * @throws ValidationException
     */
    public static String getResponseByPost(String url, String requestXML, Map<String, String> headerMap)
            throws ValidationException {
        if (StringUtils.isBlank(requestXML)) {
            return getResponseByGet(getHttpClient(), url);
        } else {
            return getResponseByPost(getHttpClient(), url, requestXML, headerMap);
        }
    }

    /**
     * get response by post method
     * @param httpClient
     * @param url
     * @param paramsMap
     * @return
     * @throws ValidationException
     */
    private static String getResponseByPost(HttpClient httpClient, String url, Map<String, String> paramsMap,
            String requestXML, Map<String, String> headerMap) throws ValidationException {
        List<NameValuePair> params = mapToPair(paramsMap);
        return getResponseByPost(httpClient, url, params, requestXML, headerMap);
    }

    private static String getResponseByPost(HttpClient httpClient, String url, String requestXML,
            Map<String, String> headerMap) throws ValidationException {
        List<NameValuePair> params = null;
        return getResponseByPost(httpClient, url, params, requestXML, headerMap);
    }

    public static List<NameValuePair> mapToPair(Map<String, String> paramsMap) {
        List<NameValuePair> params = null;
        if (MapUtils.isNotEmpty(paramsMap)) {
            params = new ArrayList<NameValuePair>();
            for (String name : paramsMap.keySet()) {
                params.add(new BasicNameValuePair(name, paramsMap.get(name)));
            }
        }
        return params;
    }

    private static String getResponseByPost(HttpClient httpClient, String url, List<NameValuePair> params,
            String requestXML, Map<String, String> headerMap) throws ValidationException {
        return getResponseByPost(httpClient, url, params, requestXML, headerMap, Consts.UTF_8);
    }

    private static String getResponseByPost(HttpClient httpClient, String url, List<NameValuePair> params,
            String requestXML, Map<String, String> headerMap, Charset charset) throws ValidationException {
        if (logger.isDebugEnabled()) {
            logger.debug("post data to " + url);
        }
        HttpPost httpPost = new HttpPost(url);
        try {
            //add params
            if (CollectionUtils.isNotEmpty(params)) {
                if (logger.isDebugEnabled()) {
                    logger.debug("post params is " + JsonUtils.toJson(params));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(params, charset));
            } else {
                StringEntity reqEntity = new StringEntity(requestXML);
                httpPost.setEntity(reqEntity);
            }
            //add http header
            if (MapUtils.isNotEmpty(headerMap)) {
                List<Header> headers = new ArrayList<Header>();
                for (String name : headerMap.keySet()) {
                    Header header = new BasicHeader(name, headerMap.get(name));
                    headers.add(header);
                }
                httpPost.setHeaders(headers.toArray(headers.toArray(new Header[headers.size()])));
            }
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String output = "";
            int statusCode = response.getStatusLine().getStatusCode();
            if (HttpStatus.SC_OK == statusCode) {
                if (null != entity) {
                    output = EntityUtils.toString(entity);
                }
            } else {
                throw new ValidationException("error code is :" + statusCode);
            }
            if (null != entity) {
                EntityUtils.consume(entity);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("return data " + output);
            }
            return output;
        } catch (UnsupportedEncodingException e) {
            httpPost.abort();
            throw new ValidationException("URL encoding error", e);
        } catch (ClientProtocolException e) {
            httpPost.abort();
            throw new ValidationException("Get URL content HTTP error", e);
        } catch (IOException e) {
            httpPost.abort();
            throw new ValidationException("Get URL content IO error", e);
        }

    }

    /**
     * check url status
     * @param url
     * @return 
     */
    public static int getUrlStatus(String url) {
        if (!url.matches("http://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?")) {
            return 0;
        }
        int statusCode = 0;
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = getHttpClient().execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (null != entity) {
                EntityUtils.consume(entity);
            }
        } catch (ClientProtocolException e) {
            httpGet.abort();
        } catch (IOException e) {
            httpGet.abort();
        }
        return statusCode;
    }

    /**
     * 将bean值转化为http请求参数
    * @param obj
    * @return
    */
    public static Collection<? extends NameValuePair> toParams(Object obj) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        toParams(params, obj);
        return params;
    }

    /**
     * 将bean值转化为http请求参数
    * @param obj
    * @return
    */
    public static void toParams(List<NameValuePair> params, Object obj) {
        toParams(params, null, obj);
    }

    /**
     * 将bean值转化为http请求参数
    * @param obj
    * @return
    */
    public static void toParams(Map<String, String> params, Object obj) {
        toParams(null, params, obj);
    }

    /** 
     * 将list转为http请求参数
    * @param list
    * @param coll
    * @param prefix
    */
    public static void toParams(List<NameValuePair> list, Collection<?> coll, String prefix) {
        toParams(list, null, coll, prefix);
    }

    private static void toParams(List<NameValuePair> list, Map<String, String> map, Object obj) {
        toParams(list, map, obj, null);
    }

    //内部转换
    @SuppressWarnings("rawtypes")
    private static void toParams(List<NameValuePair> list, Map<String, String> map, Object obj, String prefix) {
        if (obj instanceof Collection) {
            Collection coll = (Collection) obj;
            if (CollectionUtils.isNotEmpty(coll)) {
                for (Object object : coll) {
                    toParams(list, map, object, prefix);
                }
            }
        } else if (obj instanceof Object[]) {
            Collection coll = Arrays.asList((Object[]) obj);
            toParams(list, map, coll, prefix);
        } else {
            Class<? extends Object> cls = obj.getClass();
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                String name = field.getName();
                try {
                    PropertyDescriptor desc = BeanUtils.getPropertyDescriptor(cls, name);
                    Method readMethod = desc == null ? null : desc.getReadMethod();
                    Object val = readMethod == null ? null : readMethod.invoke(obj);
                    if (val == null) {
                        continue;
                    }
                    prefix = put(list, map, name, val, prefix);
                    toParams(list, map, val, prefix);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static String put(List<NameValuePair> list, Map<String, String> map, String name, Object val, String prefix) {
        prefix = StringUtils.trimToEmpty(prefix);
        String key = prefix + (StringUtils.isNotBlank(prefix) ? "." : StringUtils.EMPTY) + name;
        Object value = null;
        String result = key;
        if (val instanceof Collection) {
            value = ((Collection) val).size();
        } else if (val instanceof Object[]) {
            value = ((Object[]) val).length;
        } else {
            result = StringUtils.isBlank(prefix) ? prefix : prefix;
            value = val.toString();
        }
        if (value == null) {
            return StringUtils.EMPTY;
        }
        if (list != null) {
            list.add(new BasicNameValuePair(key, value.toString()));
        }
        if (map != null) {
            if (map.containsKey(key)) {
                Set<String> keysSet = map.keySet();
                int idx = 0;
                for (String skey : keysSet) {
                    if (skey.startsWith(key)) {
                        idx++;
                    }
                }
                result = key + "_" + idx;
                map.put(result, value.toString());
            } else {
                map.put(key, value.toString());
            }
        }
        return result;
    }
}
