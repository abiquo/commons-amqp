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
public class AsyncBPMResponseConfiguration extends AMQPConfiguration
{
    private static final String BPM_RESPONSE_EXCHANGE = "abiquo.bpm.async";

    private static final String BPM_RESPONSE_ROUTING_KEY = "abiquo.bpm.async.notifications";

    private static final String BPM_RESPONSE_QUEUE = BPM_RESPONSE_ROUTING_KEY;

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
        return BPM_RESPONSE_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return BPM_RESPONSE_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return BPM_RESPONSE_QUEUE;
    }
}
