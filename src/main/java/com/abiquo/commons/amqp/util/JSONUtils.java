/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A collection of helper methods to wrap the use of Jackson.
 * 
 * @author eruiz@abiquo.com
 */
public class JSONUtils
{
    private final static Logger LOGGER = LoggerFactory.getLogger(JSONUtils.class);

    /**
     * Serializes the given Object to JSON and returns the result in array of bytes.
     * 
     * @param value The object to serialize.
     * @return A "" array representing the JSON serialization of the object. A null value if the
     *         serialization fails.
     */
    public static byte[] serialize(Object value)
    {
        ObjectMapper mapper = new ObjectMapper();

        try
        {
            return mapper.writeValueAsBytes(value);
        }
        catch (Exception e)
        {
            LOGGER.error(String.format("Can not serialize %s to byte array.", value.getClass()
                .getSimpleName()), e);
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
    public static <T> T deserialize(byte[] bytes, Class<T> type)
    {
        ObjectMapper mapper = new ObjectMapper();
        String content = new String(bytes);

        try
        {
            return (T) mapper.readValue(content, type);
        }
        catch (Exception e)
        {
            LOGGER.error(
                String.format("Can not deserialize %s from byte array.", type.getSimpleName()), e);
            return null;
        }
    }
}
