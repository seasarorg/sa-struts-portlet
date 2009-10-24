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
package org.seasar.struts.portlet.filter;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.framework.util.StringUtil;
import org.seasar.struts.config.S2ExecuteConfig;
import org.seasar.struts.filter.RoutingFilter;
import org.seasar.struts.portlet.servlet.SAStrutsRequest;
import org.seasar.struts.portlet.util.PortletUtil;
import org.seasar.struts.portlet.util.ServletUtil;

/**
 * This class is RoutingFilter for a portlet environment.
 * 
 * @author shinsuke
 * 
 */
public class PortletRoutingFilter extends RoutingFilter {
    /**
     * a key to check if this filter is already used.
     */
    public static final String DONE = "org.seasar.struts.portlet.filter.PortletRoutingFilter.DONE";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (PortletUtil.isPortletRequest(request)) {
            PortletRequest portletRequest = PortletUtil
                    .getPortletRequest(request);
            if (PortletUtil.isSAStrutsStarted(request)
                    && portletRequest.getAttribute(DONE) == null) {
                portletRequest.setAttribute(DONE, Boolean.TRUE);
                super.doFilter(request, response, chain);
                if (PortletUtil.isActionRequest(request)) {
                    chain.doFilter(request, response);
                }

            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    protected void forward(HttpServletRequest request,
            HttpServletResponse response, String actionPath, String paramPath,
            S2ExecuteConfig executeConfig) throws IOException, ServletException {
        String servletPath = actionPath + ".do";
        String forwardPath = String.valueOf(servletPath);
        String queryString = null;
        if (executeConfig != null) {
            queryString = executeConfig.getQueryString(paramPath);
            forwardPath = forwardPath + queryString;
        }

        SAStrutsRequest sRequest = ServletUtil.unwrapSAStrutsRequest(request);
        if (sRequest != null) {
            sRequest.setContextPath(request.getContextPath());
            sRequest.setRequestURI(request.getContextPath() + forwardPath);
            sRequest.setServletPath(servletPath);
            sRequest.setPathInfo(null);
            if (!StringUtil.isEmpty(queryString)) {
                sRequest.setQueryString(queryString.substring(1));
            } else {
                sRequest.setQueryString(null);
            }
        }

        if (!PortletUtil.isActionRequest(request)) {
            request.getRequestDispatcher(forwardPath)
                    .forward(request, response);
        }
    }
}
