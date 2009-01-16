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
package org.seasar.struts.portlet.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.config.ForwardConfig;
import org.apache.struts.util.RequestUtils;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.config.S2ActionMapping;
import org.seasar.struts.config.S2ExecuteConfig;
import org.seasar.struts.portlet.servlet.SAStrutsActionRequest;
import org.seasar.struts.portlet.servlet.SAStrutsRequest;
import org.seasar.struts.portlet.util.PortletUtil;
import org.seasar.struts.util.S2ActionMappingUtil;
import org.seasar.struts.util.S2ExecuteConfigUtil;

/**
 * This class is org.seasar.struts.action.S2RequestProcessor for a portlet
 * environment.
 * 
 * @author shinsuke
 * 
 */
public class S2RequestProcessor extends
        org.seasar.struts.action.S2RequestProcessor {

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if (PortletUtil.isPortletRequest(request)) {
            String forwardPath = (String) request
                    .getAttribute(PortletUtil.FORWARD_PATH);
            if (forwardPath != null) {
                if (PortletUtil.isRenderRequest(request)) {
                    // forward
                    doForward(forwardPath, request, response);
                }
            } else {
                super.process(request, response);
            }
        } else {
            super.process(request, response);
        }
    }

    @Override
    protected void processForwardConfig(HttpServletRequest request,
            HttpServletResponse response, ForwardConfig forward)
            throws IOException, ServletException {

        if (forward == null) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("processForwardConfig(" + forward + ")");
        }

        String forwardPath = forward.getPath();
        String uri = null;

        // paths not starting with / should be passed through without any
        // processing
        // (ie. they're absolute)
        if (forwardPath.startsWith("/")) {
            uri = RequestUtils.forwardURL(request, forward, null); // get module
            // relative
            // uri
        } else {
            uri = forwardPath;
        }

        if (forward.getRedirect()) {
            if (PortletUtil.isPortletRequest(request)) {
                if (PortletUtil.isActionRequest(request)) {
                    if (uri.indexOf(":") == -1) {
                        // set processActionConfig
                        if (request instanceof SAStrutsActionRequest) {
                            String contextPath = PortletUtil.getActionRequest(
                                    request).getContextPath();
                            ((SAStrutsActionRequest) request)
                                    .getProcessActionConfig().init(
                                            contextPath + uri, contextPath,
                                            request.getCharacterEncoding());
                        }
                    } else {
                        PortletUtil.getActionRequest(request).setAttribute(
                                PortletUtil.REDIRECT, Boolean.TRUE);
                        response.sendRedirect(response.encodeRedirectURL(uri));
                    }
                }
            } else {
                // only prepend context path for relative uri
                if (uri.startsWith("/")) {
                    uri = request.getContextPath() + uri;
                }
                response.sendRedirect(response.encodeRedirectURL(uri));
            }
        } else {
            if (PortletUtil.isPortletRequest(request)) {
                if (PortletUtil.isActionRequest(request)) {
                    PortletUtil.getActionRequest(request).setAttribute(
                            PortletUtil.FORWARD_PATH, uri);
                    if (isExporablePath(uri)) {
                        exportPropertiesToRequest(request, S2ActionMappingUtil
                                .getActionMapping(), S2ExecuteConfigUtil
                                .getExecuteConfig());
                    }
                } else {
                    doForward(uri, request, response);
                }
            } else {
                doForward(uri, request, response);
            }
        }
    }

    @Override
    protected void doForward(String uri, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        if (request instanceof SAStrutsRequest) {
            SAStrutsRequest sRequest = (SAStrutsRequest) request;
            sRequest.setRequestURI(request.getContextPath() + uri);
            int index = uri.indexOf("?");
            String queryString = null;
            if (index == -1) {
                sRequest.setServletPath(uri);
                queryString = null;
            } else {
                sRequest.setServletPath(uri.substring(0, index));
                queryString = uri.substring(index + 1);
            }
            // TODO pathInfo
            sRequest.setPathInfo(null);
            if (!StringUtil.isEmpty(queryString)) {
                sRequest.setQueryString(queryString);
            } else {
                sRequest.setQueryString(null);
            }
        }

        super.doForward(uri, request, response);
    }

    @Override
    protected void doInclude(String uri, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {

        if (request instanceof SAStrutsRequest) {
            SAStrutsRequest sRequest = (SAStrutsRequest) request;
            sRequest.setRequestURI(request.getContextPath() + uri);
            int index = uri.indexOf("?");
            String queryString = null;
            if (index == -1) {
                sRequest.setServletPath(uri);
                queryString = null;
            } else {
                sRequest.setServletPath(uri.substring(0, index));
                queryString = uri.substring(index + 1);
            }
            // TODO pathInfo
            sRequest.setPathInfo(null);
            if (!StringUtil.isEmpty(queryString)) {
                sRequest.setQueryString(queryString);
            } else {
                sRequest.setQueryString(null);
            }
        }

        super.doInclude(uri, request, response);
    }

    @Override
    protected void exportPropertiesToRequest(HttpServletRequest request,
            S2ActionMapping actionMapping, S2ExecuteConfig executeConfig) {
        if (request.getAttribute(PortletUtil.FORWARD_PATH) == null
                || !PortletUtil.isRenderRequest(request)) {
            super.exportPropertiesToRequest(request, actionMapping,
                    executeConfig);
        }
    }

}
