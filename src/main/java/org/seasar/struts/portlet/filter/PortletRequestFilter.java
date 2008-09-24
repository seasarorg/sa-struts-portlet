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
package org.seasar.struts.portlet.filter;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.struts.portlet.config.ProcessActionConfig;
import org.seasar.struts.portlet.servlet.SAStrutsRenderRequest;
import org.seasar.struts.portlet.servlet.SAStrutsRenderResponse;
import org.seasar.struts.portlet.servlet.TemporaryOutputStream;
import org.seasar.struts.portlet.util.PortletUtil;

/**
 * @author shinsuke
 * 
 */
public class PortletRequestFilter implements Filter {

    protected static final String DONE = "org.seasar.struts.portlet.filter.PortletRequestFilter.done";

    private static final String CONTENT_ONLY = "contentOnly";

    protected boolean contentOnly;

    protected ServletContext servletContext;

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig config) throws ServletException {
        String value = config.getInitParameter(CONTENT_ONLY);
        if (value != null && "false".equalsIgnoreCase(value)) {
            contentOnly = false;
        } else {
            contentOnly = true;
        }
        servletContext = config.getServletContext();
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (PortletUtil.isPortletRequest(request)) {
            PortletRequest portletRequest = PortletUtil
                    .getPortletRequest(request);
            if (PortletUtil.isSAStrutsStarted(request)
                    && portletRequest.getAttribute(DONE) == null) {
                portletRequest.setAttribute(DONE, Boolean.TRUE);
                // processActionConfig
                ProcessActionConfig processActionConfig = (ProcessActionConfig) portletRequest
                        .getAttribute(PortletUtil.PROCESS_ACTION_CONFIG);
                HttpServletRequest httpServletRequest = (HttpServletRequest) request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                SAStrutsRenderRequest saStrutsRequest = new SAStrutsRenderRequest(
                        httpServletRequest, servletContext, processActionConfig);
                SAStrutsRenderResponse saStrutsResponse = new SAStrutsRenderResponse(
                        httpServletRequest, httpServletResponse, servletContext);
                // chain
                chain.doFilter(saStrutsRequest, saStrutsResponse);
                // set content
                portletRequest.setAttribute(PortletUtil.CONTENT_TYPE, response
                        .getContentType());
                portletRequest
                        .setAttribute(PortletUtil.PORTLET_CONTENT,
                                getContent(saStrutsResponse
                                        .getTemporaryOutputStream()));
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    protected String getContent(TemporaryOutputStream tos) {
        String content = tos.toString();

        if (contentOnly) {
            int bodyBeginIdx = content.indexOf("<body");
            if (bodyBeginIdx == -1) {
                bodyBeginIdx = content.indexOf("<BODY");
            }
            if (bodyBeginIdx != -1) {
                bodyBeginIdx = content.indexOf(">", bodyBeginIdx);
            }
            bodyBeginIdx++;

            int bodyEndIdx = content.indexOf("</body");
            if (bodyEndIdx == -1) {
                bodyEndIdx = content.indexOf("</BODY");
            }

            if (bodyEndIdx == -1) {
                content = content.substring(bodyBeginIdx);
            } else {
                content = content.substring(bodyBeginIdx, bodyEndIdx);
            }
        }

        return content;
    }
}
