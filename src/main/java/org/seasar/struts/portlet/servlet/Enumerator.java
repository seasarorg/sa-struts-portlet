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

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Enumerator wraps Iterator or Collection as Enumeration.
 * 
 * @author shinsuke
 * 
 */
@SuppressWarnings("unchecked")
public class Enumerator implements Enumeration {

    private Iterator iterator = null;

    /**
     * Defines Enumeration with Collection.
     * 
     * @param collection
     */
    public Enumerator(Collection collection) {
        this(collection.iterator());
    }

    /**
     * Defines Enumeration with Iterator.
     * 
     * @param iterator
     */
    public Enumerator(Iterator iterator) {
        super();
        this.iterator = iterator;
    }

    public boolean hasMoreElements() {
        return (iterator.hasNext());
    }

    public Object nextElement() throws NoSuchElementException {
        return (iterator.next());
    }

}
