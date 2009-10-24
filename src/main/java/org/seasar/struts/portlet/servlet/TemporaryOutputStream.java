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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;

/**
 * TemporaryOutputStream caches an output stream for a response.
 * 
 * @author shinsuke
 * 
 */
public class TemporaryOutputStream extends ServletOutputStream {
    private ByteArrayOutputStream baos;

    private boolean close = false;

    /**
     * Defines a temporary output stream with a buffer size.
     * 
     * @param size
     */
    public TemporaryOutputStream(int size) {
        super();
        baos = new ByteArrayOutputStream(size);
    }

    /**
     * Defines a temporary output stream. A default buffer size is 32.
     */
    public TemporaryOutputStream() {
        this(32);
    }

    public boolean isClose() {
        return close;
    }

    @Override
    public void close() throws IOException {
        baos.close();
        close = true;
    }

    @Override
    public boolean equals(Object obj) {
        return (this == obj);
    }

    @Override
    public void flush() throws IOException {
        baos.flush();
    }

    @Override
    public int hashCode() {
        return baos.hashCode();
    }

    public void reset() {
        baos.reset();
    }

    public int size() {
        return baos.size();
    }

    public byte[] toByteArray() {
        return baos.toByteArray();
    }

    @Override
    public String toString() {
        return baos.toString();
    }

    public String toString(int hibyte) {
        return baos.toString(hibyte);
    }

    public String toString(String enc) throws UnsupportedEncodingException {
        return baos.toString(enc);
    }

    @Override
    public void write(byte[] b, int off, int len) {
        baos.write(b, off, len);
    }

    @Override
    public void write(byte[] b) throws IOException {
        baos.write(b);
    }

    @Override
    public void write(int b) {
        baos.write(b);
    }

    public void writeTo(OutputStream out) throws IOException {
        baos.writeTo(out);
    }

}
