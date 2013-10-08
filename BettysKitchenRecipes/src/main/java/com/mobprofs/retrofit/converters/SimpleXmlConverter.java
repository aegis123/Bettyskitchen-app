/*
 * Copyright (C) 2013 Mobile Professionals
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobprofs.retrofit.converters;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.filter.Filter;
import org.simpleframework.xml.strategy.Strategy;
import org.simpleframework.xml.stream.Format;
import org.simpleframework.xml.transform.Matcher;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.Map;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * A {@link retrofit.converter.Converter} which uses Simple XML for serialization and deserialization of entities.
 *
 * @author Sebastian Engel (s.engel@mobile-professionals.com)
 * @see <a href="http://simple.sourceforge.net/">http://simple.sourceforge.net/</a>
 */
public class SimpleXmlConverter implements Converter {

    private Serializer serializer;

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister} as serializer.
     */
    public SimpleXmlConverter() {
        this.serializer = new Persister();
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.filter.Filter)} as serializer.
     *
     * @param filter
     */
    public SimpleXmlConverter(Filter filter) {
        this.serializer = new Persister(filter);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.filter.Filter, org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param filter
     * @param format
     */
    public SimpleXmlConverter(Filter filter, Format format) {
        this.serializer = new Persister(filter, format);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.filter.Filter, org.simpleframework.xml.transform.Matcher)} as serializer.
     *
     * @param filter
     * @param matcher
     */
    public SimpleXmlConverter(Filter filter, Matcher matcher) {
        this.serializer = new Persister(filter, matcher);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.filter.Filter, org.simpleframework.xml.transform.Matcher, org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param filter
     * @param matcher
     * @param format
     */
    public SimpleXmlConverter(Filter filter, Matcher matcher, Format format) {
        this.serializer = new Persister(filter, matcher, format);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param format
     */
    public SimpleXmlConverter(Format format) {
        this.serializer = new Persister(format);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(java.util.Map)} as serializer.
     *
     * @param filter
     */
    public SimpleXmlConverter(Map filter) {
        this.serializer = new Persister(filter);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(java.util.Map, org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param filter
     * @param format
     */
    public SimpleXmlConverter(Map filter, Format format) {
        this.serializer = new Persister(filter, format);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.transform.Matcher)} as serializer.
     *
     * @param matcher
     */
    public SimpleXmlConverter(Matcher matcher) {
        this.serializer = new Persister(matcher);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.transform.Matcher, org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param matcher
     * @param format
     */
    public SimpleXmlConverter(Matcher matcher, Format format) {
        this.serializer = new Persister(matcher, format);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy)} as serializer.
     *
     * @param strategy
     */
    public SimpleXmlConverter(Strategy strategy) {
        this.serializer = new Persister(strategy);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy, org.simpleframework.xml.filter.Filter)} as serializer.
     *
     * @param strategy
     * @param filter
     */
    public SimpleXmlConverter(Strategy strategy, Filter filter) {
        this.serializer = new Persister(strategy, filter);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy, org.simpleframework.xml.filter.Filter, org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param strategy
     * @param filter
     * @param format
     */
    public SimpleXmlConverter(Strategy strategy, Filter filter, Format format) {
        this.serializer = new Persister(strategy, filter, format);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy, org.simpleframework.xml.filter.Filter, org.simpleframework.xml.transform.Matcher)} as serializer.
     *
     * @param strategy
     * @param filter
     * @param matcher
     */
    public SimpleXmlConverter(Strategy strategy, Filter filter, Matcher matcher) {
        this.serializer = new Persister(strategy, filter, matcher);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy, org.simpleframework.xml.filter.Filter, org.simpleframework.xml.transform.Matcher, org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param strategy
     * @param filter
     * @param matcher
     * @param format
     */
    public SimpleXmlConverter(Strategy strategy, Filter filter, Matcher matcher, Format format) {
        this.serializer = new Persister(strategy, filter, matcher, format);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy, org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param strategy
     * @param format
     */
    public SimpleXmlConverter(Strategy strategy, Format format) {
        this.serializer = new Persister(strategy, format);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy, java.util.Map)} as serializer.
     *
     * @param strategy
     * @param data
     */
    public SimpleXmlConverter(Strategy strategy, Map data) {
        this.serializer = new Persister(strategy, data);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy, java.util.Map, org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param strategy
     * @param data
     * @param format
     */
    public SimpleXmlConverter(Strategy strategy, Map data, Format format) {
        this.serializer = new Persister(strategy, data, format);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy, org.simpleframework.xml.transform.Matcher)} as serializer.
     *
     * @param matcher
     */
    public SimpleXmlConverter(Strategy strategy, Matcher matcher) {
        this.serializer = new Persister(strategy, matcher);
    }

    /**
     * Constructs a SimpleXmlConverter using an instance of {@link Persister#Persister(org.simpleframework.xml.strategy.Strategy, org.simpleframework.xml.transform.Matcher, org.simpleframework.xml.stream.Format)} as serializer.
     *
     * @param strategy
     * @param matcher
     * @param format
     */
    public SimpleXmlConverter(Strategy strategy, Matcher matcher, Format format) {
        this.serializer = new Persister(matcher);
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        String charset = "UTF-8";
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType());
        }

        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(body.in(), charset);

            // Need a Class instance here, as using the Type in serializer.read(...) doesn't work
            Class<?> typeClass = (Class<?>) type;

            return serializer.read((Class<?>) type, isr, false);
        } catch (Exception e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        StringWriter stringWriter = new StringWriter();
        try {
            serializer.write(object, stringWriter);
            return new XmlTypedOutput(stringWriter.toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class XmlTypedOutput implements TypedOutput {

        private final byte[] xmlBytes;

        XmlTypedOutput(byte[] xmlBytes) {
            this.xmlBytes = xmlBytes;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return "application/xml; charset=UTF-8";
        }

        @Override
        public long length() {
            return xmlBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(xmlBytes);
        }
    }

}