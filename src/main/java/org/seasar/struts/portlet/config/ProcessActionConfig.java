/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.struts.portlet.config;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.seasar.framework.util.StringUtil;
import org.seasar.struts.portlet.util.ServletUtil;

/**
 * ProcessActionConfig keeps an access information with a request parameter,
 * attributes, path and the like. Using this class, a request instance is
 * created.
 * 
 * @author shinsuke
 * 
 */
public class ProcessActionConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    private String pathInfo;

    private String queryString;

    private String requestURI;

    private String servletPath;

    private String contextPath;

    private String characterEncoding;

    private Map parameterMap;

    private Map attributeMap;

    private String forwardPath;

    private transient Map queryParameterMap;

    private transient Map cachedParameterMap;

    /**
     * Defines a process info with pathes.
     * 
     * @param requestUrl
     * @param contextPath
     * @param characterEncoding
     */
    public ProcessActionConfig(String requestUrl, String contextPath,
            String characterEncoding) {
        init(requestUrl, contextPath, characterEncoding);
    }

    public void init(String requestUrl, String contextPath,
            String characterEncoding) {
        this.contextPath = contextPath;
        int qPos = requestUrl.indexOf("?");
        if (qPos == -1) {
            try {
                requestURI = URLDecoder.decode(requestUrl, characterEncoding);
            } catch (UnsupportedEncodingException e) {
                requestURI = requestUrl;
            }
            queryString = null;
        } else {
            try {
                requestURI = URLDecoder.decode(requestUrl.substring(0, qPos),
                        characterEncoding);
            } catch (UnsupportedEncodingException e) {
                requestURI = requestUrl.substring(0, qPos);
            }
            queryString = requestUrl.substring(qPos + 1);
        }
        if (!StringUtil.isEmpty(contextPath)) {
            servletPath = requestURI.substring(contextPath.length());
        }
        this.parameterMap = new HashMap();
        this.attributeMap = new HashMap();
        this.forwardPath = null;
        this.characterEncoding = characterEncoding;

        parseQueryParameterMap();
    }

    public String getRequestURL() {
        StringBuilder buf = new StringBuilder();
        buf.append(requestURI);
        if (pathInfo != null) {
            buf.append(pathInfo);
        }
        if (queryString != null) {
            buf.append("?");
            buf.append(queryString);
        }
        return buf.toString();
    }

    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
        parseQueryParameterMap();
    }

    public String getRequestURI() {
        return requestURI;
    }

    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    public String getServletPath() {
        return servletPath;
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public void setCharacterEncoding(String characterEncoding) {
        this.characterEncoding = characterEncoding;
    }

    public Map getParameterMap() {
        if (cachedParameterMap == null) {
            cachedParameterMap = new HashMap();
            cachedParameterMap.putAll(parameterMap);
            if (queryParameterMap == null) {
                parseQueryParameterMap();
            }
            cachedParameterMap.putAll(queryParameterMap);
        }
        return cachedParameterMap;
    }

    public void setParameterMap(Map parameterMap) {
        this.parameterMap = parameterMap;
        this.cachedParameterMap = null;
    }

    public Map getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map attributeMap) {
        this.attributeMap = attributeMap;
    }

    public String getForwardPath() {
        return forwardPath;
    }

    public void setForwardPath(String forwardPath) {
        this.forwardPath = forwardPath;
    }

    public void parseQueryParameterMap() {
        this.cachedParameterMap = null;
        this.queryParameterMap = ServletUtil.parseQueryParameterMap(
                queryString, characterEncoding);
    }
}
