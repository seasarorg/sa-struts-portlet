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
package org.seasar.struts.portlet.util;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.ServletRequest;

/**
 * This is a utility class for accessing to a portlet.
 * 
 * @author shinsuke
 * 
 */
public class PortletUtil {
    public static final String REQUEST_URL = "org.seasar.struts.portlet.request_url";

    public static final String REQUEST_URI = "javax.servlet.include.request_uri";

    public static final String CONTEXT_PATH = "javax.servlet.include.context_path";

    public static final String SERVLET_PATH = "javax.servlet.include.servlet_path";

    public static final String PATH_INFO = "javax.servlet.include.path_info";

    public static final String QUERY_STRING = "javax.servlet.include.query_string";

    public static final String PORTLET_CONFIG = "javax.portlet.config";

    public static final String PORTLET_REQUEST = "javax.portlet.request";

    public static final String PORTLET_RESPONSE = "javax.portlet.response";

    public static final String PROCESS_ACTION_CONFIG = "org.seasar.struts.portlet.process_action_config";

    public static final String PROCESS_ACTION_CONFIG_MAP = "org.seasar.struts.portlet.process_action_config_map";

    public static final String SASTRUTS_STARTED = "org.seasar.struts.portlet.started";

    public static final String CONTENT_TYPE = "org.seasar.struts.portlet.content_type";

    public static final String PORTLET_CONTENT = "org.seasar.struts.portlet.content";

    public static final String POST_METHDO = "POST";

    public static final String GET_METHDO = "GET";

    public static final String FORWARD_PATH = "org.seasar.struts.portlet.forward_path";

    public static final String ACCESS_ID = "org.seasar.struts.portlet.access_id";

    public static final String REDIRECT = "org.seasar.struts.portlet.redirect";

    public static final String ERROR_STATUS = "org.seasar.struts.portlet.error_status";

    public static final String ERROR_MESSAGE = "org.seasar.struts.portlet.error_message";

    public static final String ACTION_SERVLET = "org.seasar.struts.portlet.action_servlet";

    private PortletUtil() {
    }

    public static boolean isPortletRequest(ServletRequest request) {
        return request.getAttribute(PORTLET_REQUEST) != null;
    }

    public static boolean isActionRequest(ServletRequest request) {
        Object req = request.getAttribute(PORTLET_REQUEST);
        if (req instanceof ActionRequest) {
            return true;
        }
        return false;
    }

    public static boolean isRenderRequest(ServletRequest request) {
        Object req = request.getAttribute(PORTLET_REQUEST);
        if (req instanceof RenderRequest) {
            return true;
        }
        return false;
    }

    public static PortletRequest getPortletRequest(ServletRequest request) {
        Object req = request.getAttribute(PORTLET_REQUEST);
        if (req instanceof PortletRequest) {
            return (PortletRequest) req;
        }
        return null;
    }

    public static ActionRequest getActionRequest(ServletRequest request) {
        Object req = request.getAttribute(PORTLET_REQUEST);
        if (req instanceof ActionRequest) {
            return (ActionRequest) req;
        }
        return null;
    }

    public static RenderResponse getRenderResponse(ServletRequest request) {
        Object response = request.getAttribute(PORTLET_RESPONSE);
        if (response instanceof RenderResponse) {
            return (RenderResponse) response;
        }
        return null;
    }

    public static boolean isSAStrutsStarted(ServletRequest request) {
        PortletRequest portletRequest = getPortletRequest(request);
        if (portletRequest != null) {
            return portletRequest.getAttribute(SASTRUTS_STARTED) != null;
        }
        return false;
    }

    public static void setSAStrutsStarted(PortletRequest request) {
        request.setAttribute(SASTRUTS_STARTED, Boolean.TRUE);
    }

    public static void setSAStrutsFinished(PortletRequest request) {
        request.removeAttribute(SASTRUTS_STARTED);
    }

    public static Integer incrementAccessId(PortletRequest request) {
        PortletSession portletSession = request.getPortletSession();
        Integer accessId = (Integer) portletSession.getAttribute(ACCESS_ID);
        if (accessId == null) {
            accessId = Integer.valueOf(1);
        } else {
            accessId = Integer.valueOf(accessId.intValue() + 1);
        }
        portletSession.setAttribute(ACCESS_ID, accessId);
        return accessId;
    }

    public static Integer getAccessId(PortletRequest request) {
        PortletSession portletSession = request.getPortletSession();
        Integer accessId = (Integer) portletSession.getAttribute(ACCESS_ID);
        if (accessId == null) {
            accessId = Integer.valueOf(1);
            portletSession.setAttribute(ACCESS_ID, accessId);
        }
        return accessId;
    }

    public static Integer getAccessId(ServletRequest request) {
        Object req = request.getAttribute(PORTLET_REQUEST);
        if (req instanceof PortletRequest) {
            PortletSession portletSession = ((PortletRequest) req)
                    .getPortletSession();
            Integer accessId = (Integer) portletSession.getAttribute(ACCESS_ID);
            if (accessId == null) {
                accessId = Integer.valueOf(1);
                portletSession.setAttribute(ACCESS_ID, accessId);
            }
            return accessId;
        }
        return 0;
    }

}
