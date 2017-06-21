/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPFanoutConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

/**
 * Common RabbitMQ Broker configuration for Tracer consumer and producer.
 * 
 * @author eruiz@abiquo.com
 */
public class TracerConfiguration extends AMQPFanoutConfiguration
{
    private static final String TRACER_EXCHANGE = "abiquo.tracer";

    private static final String TRACER_QUEUE = "abiquo.tracer.traces";

    public TracerConfiguration()
    {
        super(TRACER_QUEUE);
    }

    @Override
    public AMQPFlags getFlags()
    {
        return AMQPFlags.fanout() //
            .exchangeDurable(true) //
            .queueExclusive(false) //
            .queueAutoDelete(false) //
            .build();
    }

    @Override
    public String getExchange()
    {
        return TRACER_EXCHANGE;
    }
}
