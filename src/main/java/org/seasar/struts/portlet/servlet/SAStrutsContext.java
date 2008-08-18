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

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import javax.portlet.PortletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author shinsuke
 * 
 */
public class SAStrutsContext implements ServletContext {

    private PortletContext portletContext;

    public SAStrutsContext(PortletContext portletContext) {
        this.portletContext = portletContext;
    }

    public Object getAttribute(String arg0) {
        return portletContext.getAttribute(arg0);
    }

    public Enumeration getAttributeNames() {
        return portletContext.getAttributeNames();
    }

    public String getInitParameter(String arg0) {
        return portletContext.getInitParameter(arg0);
    }

    public Enumeration getInitParameterNames() {
        return portletContext.getInitParameterNames();
    }

    public int getMajorVersion() {
        return portletContext.getMajorVersion();
    }

    public String getMimeType(String arg0) {
        return portletContext.getMimeType(arg0);
    }

    public int getMinorVersion() {
        return portletContext.getMinorVersion();
    }

    // public PortletRequestDispatcher getNamedDispatcher(String arg0) {
    // return portletContext.getNamedDispatcher(arg0);
    // }

    public String getPortletContextName() {
        return portletContext.getPortletContextName();
    }

    public String getRealPath(String arg0) {
        return portletContext.getRealPath(arg0);
    }

    // public PortletRequestDispatcher getRequestDispatcher(String arg0) {
    // return portletContext.getRequestDispatcher(arg0);
    // }

    public URL getResource(String arg0) throws MalformedURLException {
        return portletContext.getResource(arg0);
    }

    public InputStream getResourceAsStream(String arg0) {
        return portletContext.getResourceAsStream(arg0);
    }

    public Set getResourcePaths(String arg0) {
        return portletContext.getResourcePaths(arg0);
    }

    public String getServerInfo() {
        return portletContext.getServerInfo();
    }

    public void log(String arg0, Throwable arg1) {
        portletContext.log(arg0, arg1);
    }

    public void log(String arg0) {
        portletContext.log(arg0);
    }

    public void removeAttribute(String arg0) {
        portletContext.removeAttribute(arg0);
    }

    public void setAttribute(String arg0, Object arg1) {
        portletContext.setAttribute(arg0, arg1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContext#getContext(java.lang.String)
     */
    public ServletContext getContext(String s) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContext#getServlet(java.lang.String)
     */
    public Servlet getServlet(String s) throws ServletException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContext#getServletContextName()
     */
    public String getServletContextName() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContext#getServletNames()
     */
    public Enumeration getServletNames() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContext#getServlets()
     */
    public Enumeration getServlets() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContext#log(java.lang.Exception,
     * java.lang.String)
     */
    public void log(Exception exception, String s) {
        log(s, exception);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContext#getNamedDispatcher(java.lang.String)
     */
    public RequestDispatcher getNamedDispatcher(String s) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletContext#getRequestDispatcher(java.lang.String)
     */
    public RequestDispatcher getRequestDispatcher(String s) {
        // TODO Auto-generated method stub
        return null;
    }

}
