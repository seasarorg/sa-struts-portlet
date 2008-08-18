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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;

/**
 * @author shinsuke
 * 
 */
public class TemporaryOutputStream extends ServletOutputStream {
    private ByteArrayOutputStream baos;

    private boolean close = false;

    public TemporaryOutputStream(int size) {
        super();
        baos = new ByteArrayOutputStream(size);
    }

    public TemporaryOutputStream() {
        this(32);
    }

    public boolean isClose() {
        return close;
    }

    public void close() throws IOException {
        baos.close();
        close = true;
    }

    public boolean equals(Object obj) {
        return baos.equals(obj);
    }

    public void flush() throws IOException {
        baos.flush();
    }

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

    public String toString() {
        return baos.toString();
    }

    public String toString(int hibyte) {
        return baos.toString(hibyte);
    }

    public String toString(String enc) throws UnsupportedEncodingException {
        return baos.toString(enc);
    }

    public void write(byte[] b, int off, int len) {
        baos.write(b, off, len);
    }

    public void write(byte[] b) throws IOException {
        baos.write(b);
    }

    public void write(int b) {
        baos.write(b);
    }

    public void writeTo(OutputStream out) throws IOException {
        baos.writeTo(out);
    }

}
