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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author shinsuke
 * 
 */
public class SAStrutsFilterChain implements FilterChain {
    public static final int INCREMENT = 10;

    private Servlet servlet;

    private ThreadLocal filterPosition = new ThreadLocal();

    private Filter[] filters = new Filter[0];

    private int n = 0;

    public SAStrutsFilterChain(Servlet servlet) {
        this.servlet = servlet;
    }

    public void addFilter(Filter filter) {
        if (filter != null) {
            if (n == filters.length) {
                Filter[] newFilters = new Filter[n + INCREMENT];
                System.arraycopy(filters, 0, newFilters, 0, n);
                filters = newFilters;
            }
            filters[n++] = filter;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.servlet.FilterChain#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse)
     */
    public void doFilter(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        // Call the next filter if there is one
        int pos = ((Integer) filterPosition.get()).intValue();
        if (pos < n) {
            Filter filter = filters[pos++];
            filterPosition.set(new Integer(pos));

            filter.doFilter(request, response, this);

            filterPosition.set(new Integer(--pos));
            return;
        }

        // We fell off the end of the chain -- call the servlet instance
        servlet.service(request, response);

    }

    public void reset() {
        filterPosition.set(new Integer(0));
    }

}
