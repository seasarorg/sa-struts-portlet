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

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletRequestWrapper;
import javax.servlet.http.HttpServletRequestWrapper;

import org.seasar.framework.util.StringUtil;
import org.seasar.struts.portlet.servlet.SAStrutsRequest;

/**
 * This is a utility class for assessing to a servlet.
 * 
 * @author shinsuke
 * 
 */
public class ServletUtil {
    public static Map<String, String> parseQueryParameterMap(
            String queryString, String characterEncoding) {
        Map<String, String> paramMap = new HashMap<String, String>();
        if (!StringUtil.isEmpty(queryString)) {
            String[] pairs = queryString.split("&");
            for (int i = 0; i < pairs.length; i++) {
                if (!StringUtil.isEmpty(pairs[i])) {
                    String[] pair = pairs[i].split("=");
                    StringBuilder buf = new StringBuilder();
                    for (int j = 1; j < pair.length; j++) {
                        if (buf.toString().length() != 0) {
                            buf.append("=");
                        }
                        buf.append(pair[j]);
                    }
                    try {
                        paramMap.put(URLDecoder.decode(pair[0],
                                characterEncoding), URLDecoder.decode(buf
                                .toString(), characterEncoding));
                    } catch (UnsupportedEncodingException e) {
                        paramMap.put(pair[0], pair[1]);
                    }
                }
            }
        }
        return paramMap;
    }

    public static SAStrutsRequest unwrapSAStrutsRequest(ServletRequest request) {
        if (request instanceof ServletRequestWrapper) {
            if (request instanceof SAStrutsRequest) {
                return (SAStrutsRequest) request;
            } else {
                return unwrapSAStrutsRequest(ServletRequestWrapper.class.cast(
                        request).getRequest());
            }
        } else if (request instanceof SAStrutsRequest) {
            return (SAStrutsRequest) request;
        }
        return null;
    }
}
