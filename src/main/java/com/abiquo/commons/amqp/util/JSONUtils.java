/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import java.io.IOException;

import com.abiquo.commons.amqp.serialization.AMQPDeserializer;
import com.abiquo.commons.amqp.serialization.AMQPSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

/**
 * A collection of helper methods to wrap the use of Jackson. Serialize JSON to/from POJOs annotated
 * with JAXB or Jackson specific.
 * <p>
 * Prefers JSONFormat without the JAXB annotations support
 */
@Deprecated
public class JSONUtils
{
    private static final ObjectMapper om = new ObjectMapper().setAnnotationIntrospector(
        new AnnotationIntrospectorPair(new JacksonAnnotationIntrospector(),
            new JaxbAnnotationIntrospector(TypeFactory.defaultInstance())));

    private JSONUtils()
    {
    }

    public static final <T> AMQPDeserializer<T> DEFAULT_DESERIALIZER()
    {
        return JSONUtils::deserialize;
    }

    public static final <T> AMQPSerializer<T> DEFAULT_SERIALIZER()
    {
        return JSONUtils::serialize;
    }

    /**
     * Serializes the given Object to JSON and returns the result in array of bytes.
     *
     * @param value The object to serialize.
     * @return array representing the JSON serialization of the object
     * @throws IOException
     */
    public static byte[] serialize(final Object value) throws IOException
    {
        try
        {
            return om.writeValueAsBytes(value);
        }
        catch (Exception e)
        {
            throw new IOException("Cannot serialize to json", e);
        }
    }

    /**
     * Deserializes the given byte array JSON serialization.
     *
     * @param bytes A byte array representing the JSON serialization of an object.
     * @param type The Class of the object to deserialize
     * @return The deserialized object.
     * @throws IOException
     */
    public static <T> T deserialize(final byte[] bytes, final Class<T> type) throws IOException
    {
        try
        {
            return om.readValue(bytes, type);
        }
        catch (Exception e)
        {
            throw new IOException("Cannot deserialize from json", e);
        }
    }
}
