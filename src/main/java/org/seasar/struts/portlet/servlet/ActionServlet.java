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

import javax.servlet.ServletException;

import org.seasar.struts.portlet.util.PortletUtil;

/**
 * @author shinsuke
 * 
 */
public class ActionServlet extends org.apache.struts.action.ActionServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        super.init();
        getServletContext().setAttribute(PortletUtil.ACTION_SERVLET, this);
    }

    @Override
    public void destroy() {
        getServletContext().removeAttribute(PortletUtil.ACTION_SERVLET);
        super.destroy();
    }

}
