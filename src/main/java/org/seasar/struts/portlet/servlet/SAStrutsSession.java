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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.portlet.PortletContext;
import javax.portlet.PortletSession;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

/**
 * @author shinsuke
 * 
 */
public class SAStrutsSession implements HttpSession {

    private PortletSession portletSession;

    private ServletContext servletContext;

    public SAStrutsSession(PortletSession portletSession,
            ServletContext servletContext) {
        this.portletSession = portletSession;
        this.servletContext = servletContext;
    }

    public Object getAttribute(String arg0, int arg1) {
        return portletSession.getAttribute(arg0, arg1);
    }

    public Object getAttribute(String arg0) {
        return portletSession.getAttribute(arg0,
                PortletSession.APPLICATION_SCOPE);
    }

    public Enumeration getAttributeNames() {
        return portletSession
                .getAttributeNames(PortletSession.APPLICATION_SCOPE);
    }

    public Enumeration getAttributeNames(int arg0) {
        return portletSession.getAttributeNames(arg0);
    }

    public long getCreationTime() {
        return portletSession.getCreationTime();
    }

    public String getId() {
        return portletSession.getId();
    }

    public long getLastAccessedTime() {
        return portletSession.getLastAccessedTime();
    }

    public int getMaxInactiveInterval() {
        return portletSession.getMaxInactiveInterval();
    }

    public PortletContext getPortletContext() {
        return portletSession.getPortletContext();
    }

    public void invalidate() {
        portletSession.invalidate();
    }

    public boolean isNew() {
        return portletSession.isNew();
    }

    public void removeAttribute(String arg0, int arg1) {
        portletSession.removeAttribute(arg0, arg1);
    }

    public void removeAttribute(String arg0) {
        portletSession.removeAttribute(arg0, PortletSession.APPLICATION_SCOPE);
    }

    public void setAttribute(String arg0, Object arg1, int arg2) {
        portletSession.setAttribute(arg0, arg1, arg2);
    }

    public void setAttribute(String arg0, Object arg1) {
        portletSession.setAttribute(arg0, arg1,
                PortletSession.APPLICATION_SCOPE);
    }

    public void setMaxInactiveInterval(int arg0) {
        portletSession.setMaxInactiveInterval(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpSession#getServletContext()
     */
    public ServletContext getServletContext() {
        return servletContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpSession#getSessionContext()
     */
    public HttpSessionContext getSessionContext() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpSession#getValue(java.lang.String)
     */
    public Object getValue(String arg0) {
        return getAttribute(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpSession#getValueNames()
     */
    public String[] getValueNames() {
        List<String> list = new ArrayList<String>();
        for (Enumeration e = getAttributeNames(); e.hasMoreElements();) {
            list.add((String) e.nextElement());
        }
        return list.toArray(new String[] {});
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpSession#putValue(java.lang.String,
     * java.lang.Object)
     */
    public void putValue(String arg0, Object arg1) {
        setAttribute(arg0, arg1);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.http.HttpSession#removeValue(java.lang.String)
     */
    public void removeValue(String arg0) {
        removeAttribute(arg0);
    }
}
