/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class DefaultSerializer<T> implements AMQPSerializer<T>
{
    protected static final ObjectMapper objectMapper = new ObjectMapper()//
        .setAnnotationIntrospector(new AnnotationIntrospectorPair(//
            new JacksonAnnotationIntrospector(),
            new JaxbAnnotationIntrospector(TypeFactory.defaultInstance())));

    @Override
    public byte[] serialize(final T value) throws IOException
    {
        try
        {
            return objectMapper.writeValueAsBytes(value);
        }
        catch (JsonProcessingException e)
        {
            throw new IOException(e);
        }
    }
}
