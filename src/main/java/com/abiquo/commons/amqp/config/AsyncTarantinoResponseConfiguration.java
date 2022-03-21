/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

/**
 * Common RabbitMQ Broker configuration for conversion manager response consumer and producer.
 *
 * @author eruiz@abiquo.com
 */
public class AsyncTarantinoResponseConfiguration extends AMQPConfiguration
{
    private static final String TARANTINO_RESPONSE_EXCHANGE = "abiquo.virtualfactory.async";

    private static final String TARANTINO_RESPONSE_ROUTING_KEY =
        "abiquo.virtualfactory.async.notifications";

    private static final String TARANTINO_RESPONSE_QUEUE = TARANTINO_RESPONSE_ROUTING_KEY;

    @Override
    public AMQPFlags getFlags()
    {
        return AMQPFlags.direct() //
            .exchangeDurable(true) //
            .queueDurable(true) //
            .queueExclusive(false) //
            .queueAutoDelete(false) //
            .build();
    }

    @Override
    public String getExchange()
    {
        return TARANTINO_RESPONSE_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return TARANTINO_RESPONSE_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return TARANTINO_RESPONSE_QUEUE;
    }
}
