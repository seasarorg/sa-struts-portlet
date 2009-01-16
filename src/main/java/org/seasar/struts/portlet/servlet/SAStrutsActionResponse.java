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
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletContext;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.seasar.struts.portlet.util.PortletUtil;

/**
 * SAStrutsActionResponse emulates HttpServletResponse on a portlet environment.
 * This class is used on a processAction phase.
 * 
 * @author shinsuke
 * 
 */
public class SAStrutsActionResponse implements HttpServletResponse,
        ActionResponse, SAStrutsResponse {
    private ActionRequest actionRequest;

    private ActionResponse actionResponse;

    private PortletContext portletContext;

    public SAStrutsActionResponse(ActionRequest actionRequest,
            ActionResponse actionResponse, PortletContext portletContext) {
        this.actionRequest = actionRequest;
        this.actionResponse = actionResponse;
        this.portletContext = portletContext;
    }

    public void addProperty(String arg0, String arg1) {
        actionResponse.addProperty(arg0, arg1);
    }

    public String encodeURL(String arg0) {
        return actionResponse.encodeURL(arg0);
    }

    public void sendRedirect(String arg0) throws IOException {
        actionResponse.sendRedirect(arg0);
    }

    public void setPortletMode(PortletMode arg0) throws PortletModeException {
        actionResponse.setPortletMode(arg0);
    }

    public void setProperty(String arg0, String arg1) {
        actionResponse.setProperty(arg0, arg1);
    }

    public void setRenderParameter(String arg0, String arg1) {
        actionResponse.setRenderParameter(arg0, arg1);
    }

    public void setRenderParameter(String arg0, String[] arg1) {
        actionResponse.setRenderParameter(arg0, arg1);
    }

    public void setRenderParameters(Map arg0) {
        actionResponse.setRenderParameters(arg0);
    }

    public void setWindowState(WindowState arg0) throws WindowStateException {
        actionResponse.setWindowState(arg0);
    }

    public void addCookie(Cookie cookie) {
        // nothing..

    }

    public void addDateHeader(String s, long l) {
        // nothing..

    }

    public void addHeader(String s, String s1) {
        // nothing..

    }

    public void addIntHeader(String s, int i) {
        // nothing..

    }

    public boolean containsHeader(String s) {
        // nothing..
        return false;
    }

    public String encodeRedirectUrl(String s) {
        // TODO encode
        return s;
    }

    public String encodeRedirectURL(String s) {
        // TODO encode
        return s;
    }

    public String encodeUrl(String s) {
        // TODO encode
        return s;
    }

    public void sendError(int i, String s) throws IOException {
        portletContext.log("HTTP Status " + i + " - " + s);
        actionRequest
                .setAttribute(PortletUtil.ERROR_STATUS, Integer.valueOf(i));
        actionRequest.setAttribute(PortletUtil.ERROR_MESSAGE, s);
    }

    public void sendError(int i) throws IOException {
        portletContext.log("HTTP Status " + i);
        actionRequest
                .setAttribute(PortletUtil.ERROR_STATUS, Integer.valueOf(i));
    }

    public void setDateHeader(String s, long l) {
        // nothing..

    }

    public void setHeader(String s, String s1) {
        // nothing..

    }

    public void setIntHeader(String s, int i) {
        // nothing..

    }

    public void setStatus(int i, String s) {
        // nothing..

    }

    public void setStatus(int i) {
        // nothing..

    }

    public void flushBuffer() throws IOException {
        // nothing..

    }

    public int getBufferSize() {
        // nothing..
        return 0;
    }

    public String getCharacterEncoding() {
        // nothing..
        return null;
    }

    public String getContentType() {
        // nothing..
        return null;
    }

    public Locale getLocale() {
        // nothing..
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        // nothing..
        return null;
    }

    public PrintWriter getWriter() throws IOException {
        // nothing..
        return null;
    }

    public boolean isCommitted() {
        // nothing..
        return false;
    }

    public void reset() {
        // nothing..

    }

    public void resetBuffer() {
        // nothing..

    }

    public void setBufferSize(int i) {
        // nothing..

    }

    public void setCharacterEncoding(String s) {
        // nothing..

    }

    public void setContentLength(int i) {
        // nothing..

    }

    public void setContentType(String s) {
        // nothing..

    }

    public void setLocale(Locale locale) {
        // nothing..

    }
}
