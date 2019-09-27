/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.serialization;

import java.io.IOException;
import java.io.Serializable;

public class DefaultDeserializer<T extends Serializable> implements AMQPDeserializer<T>
{
    @Override
    public T deserialize(final byte[] bytes, final Class<T> type) throws IOException
    {
        try
        {
            return DefaultSerializer.objectMapper.readValue(bytes, type);
        }
        catch (Throwable t)
        {
            throw new IOException(t);
        }
    }
}
