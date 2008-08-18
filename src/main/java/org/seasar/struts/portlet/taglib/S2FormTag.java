package org.seasar.struts.portlet.taglib;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.struts.portlet.util.PortletUtil;

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
