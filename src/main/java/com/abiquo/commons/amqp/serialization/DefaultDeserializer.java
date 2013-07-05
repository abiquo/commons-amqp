/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.serialization;

import java.io.IOException;
import java.io.Serializable;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class DefaultDeserializer<T extends Serializable> implements AMQPDeserializer<T>
{
    @Override
    public T deserialize(final byte[] bytes, final Class<T> type) throws IOException
    {
        try
        {
            final String content = new String(bytes);
            return createObjectMapper().readValue(content, type);
        }
        catch (Throwable t)
        {
            throw new IOException(t);
        }
    }

    private static ObjectMapper createObjectMapper()
    {
        return new ObjectMapper()
            .setAnnotationIntrospector(new AnnotationIntrospectorPair(new JacksonAnnotationIntrospector(),
                new JaxbAnnotationIntrospector(TypeFactory.defaultInstance())));
    }
}
