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
package org.seasar.struts.portlet.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.seasar.struts.portlet.util.PortletUtil;

/**
 * SAStrutsRenderResponse emulates HttpServletResponse on a portlet environment.
 * This class is used on a render phase.
 * 
 * @author shinsuke
 * 
 */
public class SAStrutsRenderResponse extends HttpServletResponseWrapper
        implements SAStrutsResponse {
    private static final int DEFAULT_STREAM_SIZE = 4096;

    private TemporaryOutputStream tos;

    private PrintWriter printWriter;

    private HttpServletRequest request;

    private ServletContext servletContext;

    /**
     * Defines a servlet response with portlet info.
     * 
     * @param request
     * @param response
     * @param servletContext
     */
    public SAStrutsRenderResponse(HttpServletRequest request,
            HttpServletResponse response, ServletContext servletContext) {
        super(response);
        tos = new TemporaryOutputStream(DEFAULT_STREAM_SIZE);
        this.request = request;
        this.servletContext = servletContext;
    }

    /**
     * Returns a temporary output stream.
     * 
     * @return
     */
    public TemporaryOutputStream getTemporaryOutputStream() {
        return tos;
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return tos;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        if (printWriter == null) {
            printWriter = new PrintWriter(tos, true);
        } else {
            // need to flush buffers. discard it by forwarding a request.
            printWriter.flush();
        }
        return printWriter;
    }

    @Override
    public void flushBuffer() throws IOException {
        tos.flush();
    }

    @Override
    public int getBufferSize() {
        return tos.size();
    }

    @Override
    public boolean isCommitted() {
        return tos.isClose();
    }

    @Override
    public void reset() {
        tos.reset();
        super.reset();
    }

    @Override
    public void resetBuffer() {
        tos.reset();
    }

    @Override
    public void setBufferSize(int size) {
        if (tos.size() > 0) {
            throw new IllegalStateException("Content has been written.");
        }
        tos = new TemporaryOutputStream(size);
    }

    @Override
    public void sendError(int sc) throws IOException {
        super.sendError(sc);
        servletContext.log("HTTP Status " + sc);
        request.setAttribute(PortletUtil.ERROR_STATUS, Integer.valueOf(sc));
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        super.sendError(sc, msg);
        servletContext.log("HTTP Status " + sc + " - " + msg);
        request.setAttribute(PortletUtil.ERROR_STATUS, Integer.valueOf(sc));
        request.setAttribute(PortletUtil.ERROR_MESSAGE, msg);
    }

}
