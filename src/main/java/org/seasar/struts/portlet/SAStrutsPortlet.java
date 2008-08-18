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
package org.seasar.struts.portlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.LRUMap;
import org.apache.struts.action.ActionServlet;
import org.seasar.framework.container.filter.S2ContainerFilter;
import org.seasar.framework.container.hotdeploy.HotdeployFilter;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.portlet.config.ProcessActionConfig;
import org.seasar.struts.portlet.filter.PortletRoutingFilter;
import org.seasar.struts.portlet.servlet.SAStrutsActionRequest;
import org.seasar.struts.portlet.servlet.SAStrutsActionResponse;
import org.seasar.struts.portlet.servlet.SAStrutsConfig;
import org.seasar.struts.portlet.servlet.SAStrutsFilterChain;
import org.seasar.struts.portlet.util.PortletUtil;

/**
 * @author shinsuke
 * 
 */
public class SAStrutsPortlet extends GenericPortlet {

    /**
     * Name of a parameter for a help page
     */
    protected static final String HELP_PAGE = "helpPage";

    /**
     * Name of a parameter for a edit page
     */
    protected static final String EDIT_PAGE = "editPage";

    /**
     * Name of a parameter for a view page
     */
    protected static final String VIEW_PAGE = "viewPage";

    /**
     * Name of a content type
     */
    protected static final String CONTENT_TYPE = "contentType";

    protected static final String MAX_CACHE_SIZE = "maxCacheSize";

    protected static final String ENCODING = "encoding";

    protected String defaultViewPage;

    protected String defaultEditPage;

    protected String defaultHelpPage;

    protected String contentType;

    protected SAStrutsConfig saStrutsConfig;

    protected SAStrutsFilterChain saStrutsFilterChain;

    protected int maxCacheSize;

    protected String encoding;

    @Override
    public void init() throws PortletException {
        defaultViewPage = getViewPage();
        defaultEditPage = getEditPage();
        defaultHelpPage = getHelpPage();
        contentType = getContentType();
        maxCacheSize = getMaxCacheSize();
        encoding = getCharacterEncoding();

        saStrutsConfig = new SAStrutsConfig(getPortletConfig());
        saStrutsFilterChain = createFilterChain(createActionServlet());
    }

    protected SAStrutsFilterChain createFilterChain(Servlet servlet) {
        SAStrutsFilterChain saStrutsFilterChain = new SAStrutsFilterChain(
                servlet);

        // S2ContainerFilter
        Filter s2ContainerFilter = new S2ContainerFilter();
        saStrutsFilterChain.addFilter(s2ContainerFilter);

        // HotdeployFilter
        Filter hotdeployFilter = new HotdeployFilter();
        saStrutsFilterChain.addFilter(hotdeployFilter);

        // RoutingFilter
        Filter routingFilter = new PortletRoutingFilter();
        saStrutsFilterChain.addFilter(routingFilter);

        return saStrutsFilterChain;

    }

    protected ActionServlet createActionServlet() throws PortletException {
        ActionServlet actionServlet;
        // action servlet
        String className = getPortletConfig().getInitParameter("actionClass");
        if (className == null) {
            className = "org.apache.struts.action.ActionServlet";
        }
        Class actionClass;
        try {
            actionClass = Class.forName(className);
            actionServlet = (ActionServlet) actionClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new PortletException("Could not load " + className, e);
        } catch (InstantiationException e) {
            throw new PortletException("Could not create " + className, e);
        } catch (IllegalAccessException e) {
            throw new PortletException("Could not create " + className, e);
        }
        try {
            actionServlet.init(saStrutsConfig);
        } catch (ServletException e) {
            throw new PortletException("Could not initialize " + className, e);
        }
        return actionServlet;
    }

    @Override
    public void destroy() {
    }

    protected String getViewPage() {
        return getPortletConfig().getInitParameter(VIEW_PAGE);
    }

    protected String getEditPage() {
        return getPortletConfig().getInitParameter(EDIT_PAGE);
    }

    protected String getHelpPage() {
        return getPortletConfig().getInitParameter(HELP_PAGE);
    }

    protected String getContentType() {
        return getPortletConfig().getInitParameter(CONTENT_TYPE);
    }

    protected String getCharacterEncoding() {
        String encoding = getPortletConfig().getInitParameter(ENCODING);
        if (encoding == null) {
            encoding = "UTF-8";
        }
        return encoding;
    }

    protected int getMaxCacheSize() {
        int size = 10;
        String value = getPortletConfig().getInitParameter(MAX_CACHE_SIZE);
        if (value != null) {
            try {
                size = Integer.parseInt(value);
            } catch (NumberFormatException e) {
            }
        }
        return size;
    }

    @Override
    protected void doEdit(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        renderRequest(request, response, defaultEditPage);
    }

    @Override
    protected void doHelp(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        renderRequest(request, response, defaultHelpPage);
    }

    @Override
    protected void doView(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {
        renderRequest(request, response, defaultViewPage);
    }

    @Override
    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException {
        PortletUtil.setSAStrutsStarted(request);

        String requestPath = request.getParameter(PortletUtil.REQUEST_URL);
        if (requestPath == null) {
            // nothing
            return;
        }
        Integer accessId = getAccessId(request);
        if (getProcessActionConfig(request, accessId) != null) {
            // use cache
            return;
        }

        PortletSession portletSession = request.getPortletSession();

        request.setAttribute(PortletUtil.PORTLET_REQUEST, request);
        request.setAttribute(PortletUtil.PORTLET_RESPONSE, response);
        request.setAttribute(PortletUtil.PORTLET_CONFIG, getPortletConfig());

        ProcessActionConfig processActionConfig = new ProcessActionConfig(
                requestPath, request.getContextPath(), encoding);
        // TODO multipart
        Map parameterMap = new HashMap();
        parameterMap.putAll(request.getParameterMap());
        processActionConfig.setParameterMap(parameterMap);
        SAStrutsActionRequest saStrutsActionRequest = new SAStrutsActionRequest(
                request, saStrutsConfig, processActionConfig);
        SAStrutsActionResponse saActionResponse = new SAStrutsActionResponse(
                request, response);

        // execute
        process(saStrutsActionRequest, saActionResponse);

        String forwardPath = (String) request
                .getAttribute(PortletUtil.FORWARD_PATH);
        if (forwardPath != null) {
            processActionConfig.setForwardPath(forwardPath);
        }

        // set processActionConfig
        putProcessActionConfig(request, accessId, processActionConfig);

        if (request.getAttribute(PortletUtil.REDIRECT) == null) {
            // set accessId
            response.setRenderParameter(PortletUtil.ACCESS_ID, accessId
                    .toString());
        }
    }

    protected void process(HttpServletRequest request,
            HttpServletResponse response) throws IOException, PortletException {
        saStrutsFilterChain.reset();
        try {
            saStrutsFilterChain.doFilter(request, response);
        } catch (ServletException e) {
            throw new PortletException(e);
        }
    }

    protected void renderRequest(RenderRequest request,
            RenderResponse response, String defaultPage)
            throws PortletException, IOException {
        PortletUtil.setSAStrutsStarted(request);

        Integer accessId = getAccessId(request);
        ProcessActionConfig processActionConfig = createProcessActionConfig(
                request, accessId, defaultPage, request.getContextPath());
        request.setAttribute(PortletUtil.PROCESS_ACTION_CONFIG,
                processActionConfig);

        String requestPath = getRequestPath(request); // without contextPath

        PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
                .getRequestDispatcher(requestPath);
        portletRequestDispatcher.include(request, response);

        String cType = (String) request.getAttribute(PortletUtil.CONTENT_TYPE);
        if (cType == null) {
            cType = contentType;
            if (cType == null) {
                cType = "text/html";
            }
        }
        response.setContentType(cType);

        response.getWriter().print(
                request.getAttribute(PortletUtil.PORTLET_CONTENT));
        response.flushBuffer();

        // update processActionConfig
        putProcessActionConfig(request, PortletUtil.getAccessId(request),
                processActionConfig);
        PortletUtil.incrementAccessId(request);
    }

    protected ProcessActionConfig createProcessActionConfig(
            RenderRequest request, Integer accessId, String requestUrl,
            String contextPath) {
        PortletSession portletSession = request.getPortletSession();

        ProcessActionConfig processActionConfig = getProcessActionConfig(
                request, accessId);
        if (processActionConfig != null
                && processActionConfig.getForwardPath() != null) {
            StringBuilder buf = new StringBuilder(processActionConfig
                    .getServletPath());
            if (processActionConfig.getQueryString() != null) {
                buf.append("?");
                buf.append(processActionConfig.getQueryString());
            }

            request.setAttribute(PortletUtil.REQUEST_URL, buf.toString());
            request.setAttribute(PortletUtil.FORWARD_PATH, processActionConfig
                    .getForwardPath());
        } else if (processActionConfig != null) {
            request.setAttribute(PortletUtil.REQUEST_URL, processActionConfig
                    .getRequestURL());
        } else {
            StringBuilder buf = new StringBuilder();
            if (!StringUtil.isEmpty(contextPath)) {
                buf.append(contextPath);
            }
            buf.append(requestUrl);
            processActionConfig = new ProcessActionConfig(buf.toString(),
                    contextPath, encoding);
            request.setAttribute(PortletUtil.REQUEST_URL, requestUrl);
        }

        return processActionConfig;
    }

    protected String getRequestPath(PortletRequest request) {
        String requestPath = (String) request
                .getAttribute(PortletUtil.REQUEST_URL);
        if (requestPath == null) {
            requestPath = request.getParameter(PortletUtil.REQUEST_URL);
            if (requestPath == null) {
                return null;
            }
        }
        return requestPath;
    }

    protected void putProcessActionConfig(PortletRequest request,
            Integer accessId, ProcessActionConfig processActionConfig) {
        PortletSession portletSession = request.getPortletSession();
        Map configMap = (Map) portletSession
                .getAttribute(PortletUtil.PROCESS_ACTION_CONFIG_MAP);
        if (configMap == null) {
            configMap = new LRUMap(maxCacheSize);
            portletSession.setAttribute(PortletUtil.PROCESS_ACTION_CONFIG_MAP,
                    configMap);
        }
        configMap.put(accessId, processActionConfig);
    }

    protected ProcessActionConfig getProcessActionConfig(
            PortletRequest request, Integer accessId) {
        PortletSession portletSession = request.getPortletSession();
        Map configMap = (Map) portletSession
                .getAttribute(PortletUtil.PROCESS_ACTION_CONFIG_MAP);
        if (configMap == null) {
            configMap = new LRUMap(maxCacheSize);
            portletSession.setAttribute(PortletUtil.PROCESS_ACTION_CONFIG_MAP,
                    configMap);
        }
        return (ProcessActionConfig) configMap.get(accessId);
    }

    protected Integer getAccessId(PortletRequest request) {
        Integer accessId = PortletUtil.getAccessId(request);
        String accessIdString = request.getParameter(PortletUtil.ACCESS_ID);
        if (accessIdString != null) {
            try {
                accessId = Integer.valueOf(Integer.parseInt(accessIdString));
            } catch (NumberFormatException e) {
            }
        }
        return accessId;
    }

}
