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
package org.seasar.struts.portlet.taglib;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.util.StringUtil;
import org.seasar.struts.portlet.util.PortletUtil;
import org.seasar.struts.taglib.S2Functions;
import org.seasar.struts.util.ActionUtil;
import org.seasar.struts.util.RoutingUtil;

/**
 * @author shinsuke
 * 
 */
public class S2LinkTag extends org.seasar.struts.taglib.S2LinkTag {
    protected static final String ACTION_URL_TYPE = "action";

    protected static final String RENDER_URL_TYPE = "render";

    protected static final String RESOURCE_URL_TYPE = "resource";

    private String urlType;

    public S2LinkTag() {
        super();
        urlType = null;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    @Override
    protected String calculateURL() throws JspException {
        if (href != null) {
            int index = href.indexOf(':');
            if (index > -1) {
                return super.calculateURL();
            }
            if (PortletUtil.isPortletRequest(pageContext.getRequest())) {
                RenderResponse renderResponse = PortletUtil
                        .getRenderResponse(pageContext.getRequest());
                if (renderResponse != null) {
                    PortletURL portletURL;
                    if (RESOURCE_URL_TYPE.equals(urlType)) {
                        return S2Functions.url(href);
                    } else if (ACTION_URL_TYPE.equals(urlType)) {
                        portletURL = renderResponse.createActionURL();
                    } else if (RENDER_URL_TYPE.equals(urlType)) {
                        portletURL = renderResponse.createRenderURL();
                    } else {
                        // default is an action url.
                        portletURL = renderResponse.createActionURL();
                    }
                    portletURL.setParameter(PortletUtil.REQUEST_URL,
                            functionsUrl(href));
                    portletURL.setParameter(PortletUtil.ACCESS_ID, Integer
                            .valueOf(
                                    PortletUtil.getAccessId(
                                            pageContext.getRequest())
                                            .intValue() + 1).toString());
                    return portletURL.toString();
                }
            } else {
                return S2Functions.url(href);
            }
        }
        return super.calculateURL();
    }

    protected String functionsUrl(String input) {
        String contextPath = (String) ((HttpServletRequest) pageContext
                .getRequest()).getContextPath();
        StringBuilder sb = new StringBuilder();
        if (contextPath.length() > 1) {
            sb.append(contextPath);
        }
        if (StringUtil.isEmpty(input)) {
            sb.append(ActionUtil.calcActionPath());
        } else if (!input.startsWith("/")) {
            sb.append(ActionUtil.calcActionPath()).append(input);
        } else {
            String[] names = StringUtil.split(input, "/");
            S2Container container = SingletonS2ContainerFactory.getContainer();
            StringBuilder sb2 = new StringBuilder(50);
            String input2 = input;
            for (int i = 0; i < names.length; i++) {
                if (container.hasComponentDef(sb2 + names[i] + "Action")) {
                    String actionPath = RoutingUtil.getActionPath(names, i);
                    String paramPath = RoutingUtil.getParamPath(names, i + 1);
                    if (StringUtil.isEmpty(paramPath)) {
                        input2 = actionPath + "/";
                        break;
                    }
                }
                sb2.append(names[i] + "_");
            }
            sb.append(input2);
        }
        return sb.toString();
    }

    public void release() {

        super.release();
        urlType = null;
    }
}
