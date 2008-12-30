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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortalContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletSession;
import javax.portlet.WindowState;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.struts.portlet.config.ProcessActionConfig;
import org.seasar.struts.portlet.util.PortletUtil;

/**
 * @author shinsuke
 * 
 */
public class SAStrutsActionRequest implements HttpServletRequest,
        ActionRequest, SAStrutsRequest {
    private ActionRequest actionRequest;

    private ServletConfig servletConfig;

    private ProcessActionConfig processActionConfig;

    private HttpSession httpSession;

    public SAStrutsActionRequest(ActionRequest actionRequest,
            ServletConfig servletConfig, ProcessActionConfig processActionConfig) {
        this.actionRequest = actionRequest;
        this.servletConfig = servletConfig;
        this.processActionConfig = processActionConfig;
        if (actionRequest.isRequestedSessionIdValid()) {
            this.httpSession = new SAStrutsSession(actionRequest
                    .getPortletSession(), servletConfig.getServletContext());
        } else {
            this.httpSession = null;
        }
    }

    public ProcessActionConfig getProcessActionConfig() {
        return processActionConfig;
    }

    public Object getAttribute(String arg0) {
        Object value = processActionConfig.getAttributeMap().get(arg0);
        if (value != null) {
            return value;
        }
        if (PortletUtil.SERVLET_PATH.equals(arg0)
                || PortletUtil.CONTEXT_PATH.equals(arg0)
                || PortletUtil.REQUEST_URI.equals(arg0)
                || PortletUtil.PATH_INFO.equals(arg0)
                || PortletUtil.QUERY_STRING.equals(arg0)) {
            return null;
        }
        return actionRequest.getAttribute(arg0);
    }

    public Enumeration getAttributeNames() {
        List names = new ArrayList();
        names.add(processActionConfig.getAttributeMap().keySet());
        for (Enumeration e = actionRequest.getAttributeNames(); e
                .hasMoreElements();) {
            names.add(e.nextElement());
        }
        return new Enumerator(names);
    }

    public String getAuthType() {
        return actionRequest.getAuthType();
    }

    public String getCharacterEncoding() {
        return processActionConfig.getCharacterEncoding();
    }

    public int getContentLength() {
        return actionRequest.getContentLength();
    }

    public String getContentType() {
        return actionRequest.getContentType();
    }

    public String getContextPath() {
        return processActionConfig.getContextPath();
    }

    public Locale getLocale() {
        return actionRequest.getLocale();
    }

    public Enumeration getLocales() {
        return actionRequest.getLocales();
    }

    public String getParameter(String arg0) {
        Object value = getParameterMap().get(arg0);
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

    public Map getParameterMap() {
        return processActionConfig.getParameterMap();
    }

    public Enumeration getParameterNames() {
        return (new Enumerator(getParameterMap().keySet()));
    }

    public String[] getParameterValues(String arg0) {
        Object value = getParameterMap().get(arg0);
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

    public PortalContext getPortalContext() {
        return actionRequest.getPortalContext();
    }

    public InputStream getPortletInputStream() throws IOException {
        return actionRequest.getPortletInputStream();
    }

    public PortletMode getPortletMode() {
        return actionRequest.getPortletMode();
    }

    public PortletSession getPortletSession() {
        return actionRequest.getPortletSession();
    }

    public PortletSession getPortletSession(boolean arg0) {
        return actionRequest.getPortletSession(arg0);
    }

    public PortletPreferences getPreferences() {
        return actionRequest.getPreferences();
    }

    public Enumeration getProperties(String arg0) {
        return actionRequest.getProperties(arg0);
    }

    public String getProperty(String arg0) {
        return actionRequest.getProperty(arg0);
    }

    public Enumeration getPropertyNames() {
        return actionRequest.getPropertyNames();
    }

    public BufferedReader getReader() throws UnsupportedEncodingException,
            IOException {
        return actionRequest.getReader();
    }

    public String getRemoteUser() {
        return actionRequest.getRemoteUser();
    }

    public String getRequestedSessionId() {
        return actionRequest.getRequestedSessionId();
    }

    public String getResponseContentType() {
        return actionRequest.getResponseContentType();
    }

    public Enumeration getResponseContentTypes() {
        return actionRequest.getResponseContentTypes();
    }

    public String getScheme() {
        return actionRequest.getScheme();
    }

    public String getServerName() {
        return actionRequest.getServerName();
    }

    public int getServerPort() {
        return actionRequest.getServerPort();
    }

    public Principal getUserPrincipal() {
        return actionRequest.getUserPrincipal();
    }

    public WindowState getWindowState() {
        return actionRequest.getWindowState();
    }

    public boolean isPortletModeAllowed(PortletMode arg0) {
        return actionRequest.isPortletModeAllowed(arg0);
    }

    public boolean isRequestedSessionIdValid() {
        return actionRequest.isRequestedSessionIdValid();
    }

    public boolean isSecure() {
        return actionRequest.isSecure();
    }

    public boolean isUserInRole(String arg0) {
        return actionRequest.isUserInRole(arg0);
    }

    public boolean isWindowStateAllowed(WindowState arg0) {
        return actionRequest.isWindowStateAllowed(arg0);
    }

    public void removeAttribute(String arg0) {
        if (processActionConfig.getAttributeMap().containsKey(arg0)) {
            processActionConfig.getAttributeMap().remove(arg0);
        } else {
            actionRequest.removeAttribute(arg0);
        }
    }

    public void setAttribute(String arg0, Object arg1) {
        processActionConfig.getAttributeMap().put(arg0, arg1);
    }

    public void setCharacterEncoding(String arg0)
            throws UnsupportedEncodingException {
        actionRequest.setCharacterEncoding(arg0);
    }

    public Cookie[] getCookies() {
        // TODO Auto-generated method stub
        return null;
    }

    public long getDateHeader(String s) {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getHeader(String s) {
        // TODO Auto-generated method stub
        return null;
    }

    public Enumeration getHeaderNames() {
        // return empty
        return new Enumerator(Collections.EMPTY_LIST.iterator());
    }

    public Enumeration getHeaders(String s) {
        // TODO Auto-generated method stub
        return null;
    }

    public int getIntHeader(String s) {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getMethod() {
        return PortletUtil.POST_METHDO;
    }

    public String getPathInfo() {
        return processActionConfig.getPathInfo();
    }

    public String getPathTranslated() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getQueryString() {
        return processActionConfig.getQueryString();
    }

    public String getRequestURI() {
        // TODO Auto-generated method stub
        return null;
    }

    public StringBuffer getRequestURL() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getServletPath() {
        return processActionConfig.getServletPath();
    }

    public HttpSession getSession() {
        if (httpSession == null) {
            httpSession = new SAStrutsSession(
                    actionRequest.getPortletSession(), servletConfig
                            .getServletContext());
        }
        return httpSession;
    }

    public HttpSession getSession(boolean flag) {
        if (flag) {
            httpSession = new SAStrutsSession(
                    actionRequest.getPortletSession(), servletConfig
                            .getServletContext());
        }
        return httpSession;
    }

    public boolean isRequestedSessionIdFromCookie() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isRequestedSessionIdFromUrl() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isRequestedSessionIdFromURL() {
        // TODO Auto-generated method stub
        return false;
    }

    public ServletInputStream getInputStream() throws IOException {
        return new SAStrutsServletInputStream(getPortletInputStream());
    }

    public String getLocalAddr() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getLocalName() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getLocalPort() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getProtocol() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getRealPath(String s) {
        return servletConfig.getServletContext().getRealPath(s);
    }

    public String getRemoteAddr() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getRemoteHost() {
        // TODO Auto-generated method stub
        return null;
    }

    public int getRemotePort() {
        // TODO Auto-generated method stub
        return 0;
    }

    public RequestDispatcher getRequestDispatcher(String s) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setContextPath(java
     * .lang.String)
     */
    public void setContextPath(String contextPath) {
        processActionConfig.setContextPath(contextPath);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setPathInfo(java.lang
     * .String)
     */
    public void setPathInfo(String pathInfo) {
        processActionConfig.setPathInfo(pathInfo);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setQueryString(java
     * .lang.String)
     */
    public void setQueryString(String queryString) {
        processActionConfig.setQueryString(queryString);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setRequestURI(java.
     * lang.String)
     */
    public void setRequestURI(String requestURI) {
        processActionConfig.setRequestURI(requestURI);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.seasar.struts.portlet.servlet.SAStrutsRequest#setServletPath(java
     * .lang.String)
     */
    public void setServletPath(String servletPath) {
        processActionConfig.setServletPath(servletPath);
    }

}
