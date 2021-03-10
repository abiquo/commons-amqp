/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.consumer;

import java.util.Map;

public interface AMQPCallback<T>
{
    /** Implement this when using a AMQPConsumer using ackAfterProcess=false */
    default void process(final T message, final Map<String, Object> headers)
    {
        process(message);
    }

    void process(T message);
}
