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

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author shinsuke
 * 
 */
public class SAStrutsConfig implements ServletConfig, PortletConfig {

    public static final String SERVLET_NAME = "servletName";

    private PortletConfig portletConfig;

    public SAStrutsConfig(PortletConfig portletConfig) {
        this.portletConfig = portletConfig;
    }

    public String getInitParameter(String arg0) {
        return portletConfig.getInitParameter(arg0);
    }

    public Enumeration getInitParameterNames() {
        return portletConfig.getInitParameterNames();
    }

    public PortletContext getPortletContext() {
        return portletConfig.getPortletContext();
    }

    public String getPortletName() {
        return portletConfig.getPortletName();
    }

    public ResourceBundle getResourceBundle(Locale arg0) {
        return portletConfig.getResourceBundle(arg0);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletConfig#getServletContext()
     */
    public ServletContext getServletContext() {
        return new SAStrutsContext(portletConfig.getPortletContext());
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.ServletConfig#getServletName()
     */
    public String getServletName() {
        String servletName = getInitParameter(SERVLET_NAME);
        if (servletName == null) {
            servletName = "action";
        }
        return servletName;
    }

}
