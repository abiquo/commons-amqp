/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * A collection of helper methods to wrap the use of Jackson. Serialize JSON to/from POJOs annotated
 * with JAXB or Jackson specific
 * 
 * @author eruiz@abiquo.com
 */
public class JSONUtils
{
    private final static Logger log = LoggerFactory.getLogger(JSONUtils.class);

    /**
     * Serializes the given Object to JSON and returns the result in array of bytes.
     * 
     * @param value The object to serialize.
     * @return A "" array representing the JSON serialization of the object. A null value if the
     *         serialization fails.
     */
    public static byte[] serialize(final Object value)
    {
        try
        {
            return createObjectMapper().writeValueAsBytes(value);
        }
        catch (Exception e)
        {
            log.error(String.format("Can not serialize %s to byte array.",
                value.getClass().getSimpleName()), e);
            return null;
        }
    }

    /**
     * Deserializes the given byte array JSON serialization.
     * 
     * @param bytes A byte array representing the JSON serialization of an object.
     * @param type The Class of the object to deserialize
     * @return The deserialized object or null if the process fails.
     */
    public static <T> T deserialize(final byte[] bytes, final Class<T> type)
    {
        final String content = new String(bytes);

        try
        {
            return createObjectMapper().readValue(content, type);
        }
        catch (Exception e)
        {
            log.error(
                String.format("Can not deserialize %s from byte array.", type.getSimpleName()), e);
            return null;
        }
    }

    private static ObjectMapper createObjectMapper()
    {
        return new ObjectMapper().setAnnotationIntrospector(
            new AnnotationIntrospectorPair(new JacksonAnnotationIntrospector(),
                new JaxbAnnotationIntrospector(TypeFactory.defaultInstance())));
    }
}
