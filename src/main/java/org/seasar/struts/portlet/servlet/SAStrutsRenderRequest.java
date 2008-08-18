/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.struts.portlet.servlet;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.seasar.struts.portlet.config.ProcessActionConfig;
import org.seasar.struts.portlet.util.PortletUtil;

/**
 * @author shinsuke
 * 
 */
public class SAStrutsRenderRequest extends HttpServletRequestWrapper implements
        SAStrutsRequest {
    private ProcessActionConfig processActionConfig;

    private String pathInfo;

    private String queryString;

    private String requestURI;

    private String servletPath;

    private String contextPath;

    private String charsetEncoding;

    public SAStrutsRenderRequest(HttpServletRequest request,
            ProcessActionConfig config) {
        super(request);
        processActionConfig = config;
        pathInfo = processActionConfig.getPathInfo() == null ? null : String
                .valueOf(processActionConfig.getPathInfo());
        queryString = processActionConfig.getQueryString() == null ? null
                : String.valueOf(processActionConfig.getQueryString());
        requestURI = processActionConfig.getRequestURI() == null ? null
                : String.valueOf(processActionConfig.getRequestURI());
        servletPath = processActionConfig.getServletPath() == null ? null
                : String.valueOf(processActionConfig.getServletPath());
        contextPath = processActionConfig.getContextPath() == null ? null
                : String.valueOf(processActionConfig.getContextPath());
        charsetEncoding = processActionConfig.getCharacterEncoding() == null ? null
                : String.valueOf(processActionConfig.getCharacterEncoding());
        for (Iterator itr = processActionConfig.getAttributeMap().entrySet()
                .iterator(); itr.hasNext();) {
            Map.Entry entry = (Map.Entry) itr.next();
            request.setAttribute((String) entry.getKey(), entry.getValue());
        }
        if (processActionConfig.getForwardPath() != null) {
            request.setAttribute(PortletUtil.FORWARD_PATH, processActionConfig
                    .getForwardPath());
        }
    }

    @Override
    public String getParameter(String name) {
        Object value = getParameterMap().get(name);
        if (value == null) {
            return null;
        } else if (value instanceof String) {
            return (String) value;
        } else if (value instanceof String[]) {
            return (((String[]) value)[0]);
        } else {
            return value.toString();
        }
    }

    @Override
    public Map getParameterMap() {
        return processActionConfig.getParameterMap();
    }

    @Override
    public Enumeration getParameterNames() {
        return (new Enumerator(getParameterMap().keySet()));
    }

    @Override
    public String[] getParameterValues(String name) {
        Object value = getParameterMap().get(name);
        if (value == null)
            return ((String[]) null);
        else if (value instanceof String[])
            return ((String[]) value);
        else if (value instanceof String) {
            String values[] = new String[1];
            values[0] = (String) value;
            return (values);
        } else {
            String values[] = new String[1];
            values[0] = value.toString();
            return (values);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.struts.portlet.servlet.SAStrutsRequest#getPathInfo()
     */
    @Override
    public String getPathInfo() {
        return pathInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.struts.portlet.servlet.SAStrutsRequest#getQueryString()
     */
    @Override
    public String getQueryString() {
        return queryString;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.struts.portlet.servlet.SAStrutsRequest#getRequestURI()
     */
    @Override
    public String getRequestURI() {
        return requestURI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.struts.portlet.servlet.SAStrutsRequest#getServletPath()
     */
    @Override
    public String getServletPath() {
        return servletPath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.seasar.struts.portlet.servlet.SAStrutsRequest#getContextPath()
     */
    @Override
    public String getContextPath() {
        return contextPath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setPathInfo(java.lang
     * .String)
     */
    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setQueryString(java
     * .lang.String)
     */
    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setRequestURI(java.
     * lang.String)
     */
    public void setRequestURI(String requestURI) {
        this.requestURI = requestURI;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setServletPath(java
     * .lang.String)
     */
    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setContextPath(java
     * .lang.String)
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    @Override
    public String getMethod() {
        return PortletUtil.GET_METHDO;
    }

    @Override
    public String getCharacterEncoding() {
        return charsetEncoding;
    }

    @Override
    public void setCharacterEncoding(String enc)
            throws UnsupportedEncodingException {
        this.charsetEncoding = enc;
    }

}
