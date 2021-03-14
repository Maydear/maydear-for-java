/*
 * Copyright 2008-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.maydear.core.framework.spring.web.util;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Request 工具类
 *
 * @author kelvin.liang
 * @version 1.0.0
 */
public final class RequestUtils {

    /**
     * 用户请求号-自定义的请求头标志“X-Requested-Id”
     */
    private static final String HEADER_NAME_REQUESTED_UID = "X-Requested-Id";

    /**
     * 未知的字符标记
     */
    private static final String UNKNOWN = "unknown";

    /**
     * nginx 的反向代理头标志“x-forwarded-for”
     */
    private static final String HEADER_NAME_X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * apache 的反向代理头标志“WL-Proxy-Client-IP”
     */
    private static final String HEADER_NAME_WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * apache 的反向代理头标志“Proxy-Client-IP”
     */
    private static final String HEADER_NAME_PROXY_CLIENT_IP = "Proxy-Client-IP";

    /**
     * 静态工具类不应该被实例化
     */
    private RequestUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取 HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes servletRequestAttributes = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
        return servletRequestAttributes.getRequest();
    }

    /**
     * 返回绝对路径
     *
     * @param path 相对路径
     * @return String
     */
    public static String getRealPath(String path) {
        ServletContext servletContext = getServletContext();
        Objects.requireNonNull(servletContext);
        return servletContext.getRealPath(path);
    }

    /**
     * 获取 ServletContext
     *
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        HttpServletRequest request = getRequest();
        Objects.requireNonNull(request);
        return request.getServletContext();
    }

    /**
     * 获取当前请求中的客户端IP
     *
     * @return String
     */
    public static String getRemoteIp(HttpServletRequest request) {
        Objects.requireNonNull(request);
        String ip = request.getHeader(HEADER_NAME_X_FORWARDED_FOR);

        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_NAME_PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader(HEADER_NAME_WL_PROXY_CLIENT_IP);
        }
        if (StringUtils.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取当前请求中的客户端IP
     *
     * @return String
     */
    public static String getRemoteIp() {
        HttpServletRequest request = getRequest();
        return getRemoteIp(request);
    }


    /**
     * 获取当前请求中的根 URL。格式 http://domain:port
     *
     * @return String
     */
    public static String getRootUrl() {
        return getRootUrlasStringBuffer().toString();
    }

    /**
     * 获取当前请求中的带上下文路径的 URL。格式 http://domain:port/contextPath
     *
     * @return String
     */
    public static String getContextUrl(HttpServletRequest request) {
        Objects.requireNonNull(request);
        String contextPath = request.getContextPath();
        return getRootUrlasStringBuffer() + contextPath + "/";
    }

    /**
     * 获取当前请求中的带上下文路径的 URL。格式 http://domain:port/contextPath
     *
     * @return String
     */
    public static String getContextUrl() {
        HttpServletRequest request = getRequest();
        return getContextUrl(request);
    }

    /**
     * 获取当前请求中的不包含协议的 URL。格式 domain:port/contextPath
     *
     * @return String
     */
    public static String getNoSchemeContextUrl() {
        HttpServletRequest request = getRequest();
        return getNoSchemeContextUrl(request);
    }

    /**
     * 获取当前请求中的不包含协议的 URL。格式 domain:port/contextPath
     *
     * @return String
     */
    public static String getNoSchemeContextUrl(HttpServletRequest request) {
        Objects.requireNonNull(request);
        return request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
    }

    /**
     * 获取当前请求中的 User-Agent
     *
     * @return String
     */
    public static String getUserAgent() {
        return getUserAgent(getRequest());
    }

    /**
     * 获取当前请求中的 User-Agent
     *
     * @return String
     */
    public static String getUserAgent(HttpServletRequest request) {
        return Objects.requireNonNull(request).getHeader("User-Agent");
    }

    /**
     * 获取当前请求中的匹配指定名称的Header值
     *
     * @param name 名称
     * @return Header值
     */
    public static String getHeader(String name) {
        HttpServletRequest request = getRequest();
        return getHeader(request,name);
    }

    /**
     * 获取当前请求中的匹配指定名称的Header值
     *
     * @param name 名称
     * @return Header值
     */
    public static String getHeader(HttpServletRequest request,String name) {
        return request == null ? null : request.getHeader(name);
    }

    /**
     * 获取请求的唯一标志
     *
     * @return 返回请求标志
     */
    public static UUID getRequestedId(HttpServletRequest request) {
        String requestedIdString = getHeader(request,HEADER_NAME_REQUESTED_UID);
        if (StringUtils.isNotBlank(requestedIdString)) {
            try {
                return UUID.fromString(requestedIdString);
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }
        return null;
    }

    /**
     * 获取请求的唯一标志
     *
     * @return 返回请求标志
     */
    public static UUID getRequestedId() {
        HttpServletRequest request = getRequest();
        return getRequestedId(request);
    }

    /**
     *  获取完整的url
     * @param request  HTTP Servlet的请求信息。
     * @return 返回完整url
     */
    public static String buildFullRequestUrl(HttpServletRequest request) {
        return buildFullRequestUrl(request.getScheme(), request.getServerName(), request.getServerPort(), request.getRequestURI(),request.getQueryString());
    }

    /**
     * Obtains the full URL the client used to make the request.
     * <p>
     * Note that the server port will not be shown if it is the default server port for
     * HTTP or HTTPS (80 and 443 respectively).
     * @return the full URL, suitable for redirects (not decoded).
     */
    private static String buildFullRequestUrl(String scheme, String serverName, int serverPort, String requestURI, String queryString) {
        scheme = scheme.toLowerCase();
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);
        // Only add port if not default
        if ("http".equals(scheme)) {
            if (serverPort != 80) {
                url.append(":").append(serverPort);
            }
        }
        else if ("https".equals(scheme)) {
            if (serverPort != 443) {
                url.append(":").append(serverPort);
            }
        }
        // Use the requestURI as it is encoded (RFC 3986) and hence suitable for
        // redirects.
        url.append(requestURI);
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return url.toString();
    }


    /**
     * 获取当前请求中的根 URL
     *
     * @return URL
     */
    private static StringBuffer getRootUrlasStringBuffer(HttpServletRequest request) {
        Objects.requireNonNull(request);
        String contextPath = request.getContextPath();
        StringBuffer requestUrl = request.getRequestURL();
        return requestUrl.delete(requestUrl.indexOf(contextPath), requestUrl.length());
    }

    /**
     * 获取当前请求中的根 URL
     *
     * @return URL
     */
    private static StringBuffer getRootUrlasStringBuffer() {
        HttpServletRequest request = getRequest();
        return getRootUrlasStringBuffer(request);
    }

    /**
     * 获取指定key的Url参数
     *
     * @param key Query参数Key
     * @return 返回参数值数组
     */
    public static String[] getQueryParameter(HttpServletRequest request,String key) {
        Objects.requireNonNull(request);
        Map<String, String[]> map = request.getParameterMap();

        if (map.containsKey(key)) {
            return map.get(key);
        }
        return new String[]{};
    }

    /**
     * 获取指定key的Url参数
     *
     * @param key Query参数Key
     * @return 返回参数值数组
     */
    public static String[] getQueryParameter(String key) {
        HttpServletRequest request = getRequest();
        return getQueryParameter(request,key);
    }

    /**
     * 获取指定key的Url参数
     *
     * @param key Query参数Key
     * @return Query参值得
     */
    public static String getQueryParameterFirstOrDefault(String key) {
        HttpServletRequest request = getRequest();
        return getQueryParameterFirstOrDefault(request,key);
    }

    /**
     * 获取指定key的Url参数
     *
     * @param key Query参数Key
     * @return Query参值得
     */
    public static String getQueryParameterFirstOrDefault(HttpServletRequest request ,String key) {
        String[] values = getQueryParameter(request,key);

        if (ArrayUtils.isNotEmpty(values)) {
            return values[0];
        }
        return "";
    }
}
