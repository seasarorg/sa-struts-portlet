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
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shinsuke
 * 
 */
public class SAStrutsActionResponse implements HttpServletResponse,
        ActionResponse, SAStrutsResponse {
    private ActionRequest actionRequest;

    private ActionResponse actionResponse;

    public SAStrutsActionResponse(ActionRequest actionRequest,
            ActionResponse actionResponse) {
        this.actionRequest = actionRequest;
        this.actionResponse = actionResponse;
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
        // TODO Auto-generated method stub

    }

    public void addDateHeader(String s, long l) {
        // TODO Auto-generated method stub

    }

    public void addHeader(String s, String s1) {
        // TODO Auto-generated method stub

    }

    public void addIntHeader(String s, int i) {
        // TODO Auto-generated method stub

    }

    public boolean containsHeader(String s) {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub

    }

    public void sendError(int i) throws IOException {
        // TODO Auto-generated method stub

    }

    public void setDateHeader(String s, long l) {
        // TODO Auto-generated method stub

    }

    public void setHeader(String s, String s1) {
        // TODO Auto-generated method stub

    }

    public void setIntHeader(String s, int i) {
        // TODO Auto-generated method stub

    }

    public void setStatus(int i, String s) {
        // TODO Auto-generated method stub

    }

    public void setStatus(int i) {
        // TODO Auto-generated method stub

    }

    public void flushBuffer() throws IOException {
        // TODO Auto-generated method stub

    }

    public int getBufferSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    public String getCharacterEncoding() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getContentType() {
        // TODO Auto-generated method stub
        return null;
    }

    public Locale getLocale() {
        // TODO Auto-generated method stub
        return null;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public PrintWriter getWriter() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean isCommitted() {
        // TODO Auto-generated method stub
        return false;
    }

    public void reset() {
        // TODO Auto-generated method stub

    }

    public void resetBuffer() {
        // TODO Auto-generated method stub

    }

    public void setBufferSize(int i) {
        // TODO Auto-generated method stub

    }

    public void setCharacterEncoding(String s) {
        // TODO Auto-generated method stub

    }

    public void setContentLength(int i) {
        // TODO Auto-generated method stub

    }

    public void setContentType(String s) {
        // TODO Auto-generated method stub

    }

    public void setLocale(Locale locale) {
        // TODO Auto-generated method stub

    }
}
