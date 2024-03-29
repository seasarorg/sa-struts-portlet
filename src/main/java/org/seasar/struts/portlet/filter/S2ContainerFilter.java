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

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.struts.portlet.util.PortletUtil;

/**
 * This class is S2ContainerFilter for a portlet environment.
 * 
 * @author shinsuke
 * 
 */
public class S2ContainerFilter extends
        org.seasar.framework.container.filter.S2ContainerFilter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        if (PortletUtil.isPortletRequest(request)) {
            if (PortletUtil.isSAStrutsStarted(request)) {
                super.doFilter(request, response, chain);
            } else {
                chain.doFilter(request, response);
            }
        } else {
            super.doFilter(request, response, chain);
        }
    }
}
