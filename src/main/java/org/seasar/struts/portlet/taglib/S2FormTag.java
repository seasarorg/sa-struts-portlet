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
package org.seasar.struts.portlet.taglib;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.struts.portlet.util.PortletUtil;

/**
 * S2FormTag is called by s:form. This class returns a link for a portlet.
 * 
 * @author shinsuke
 * 
 */
public class S2FormTag extends org.seasar.struts.taglib.S2FormTag {

    @Override
    protected void renderAction(StringBuffer results) {
        HttpServletRequest request = (HttpServletRequest) pageContext
                .getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext
                .getResponse();
        results.append(" action=\"");
        String contextPath = request.getContextPath();
        StringBuffer value = new StringBuffer();
        if (contextPath.length() > 1) {
            value.append(contextPath);
        }
        value.append(action);
        if (PortletUtil.isPortletRequest(request)) {
            RenderResponse renderResponse = PortletUtil
                    .getRenderResponse(request);
            if (renderResponse != null) {
                PortletURL actionUrl = renderResponse.createActionURL();
                actionUrl.setParameter(PortletUtil.REQUEST_URL, value
                        .toString());
                actionUrl.setParameter(PortletUtil.ACCESS_ID, Integer.valueOf(
                        PortletUtil.getAccessId(request).intValue() + 1)
                        .toString());
                results.append(actionUrl.toString());
            }
        } else {
            results.append(response.encodeURL(value.toString()));
        }
        results.append("\"");
    }

}
