/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPFanoutConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

public abstract class TraceBroadcast extends AMQPFanoutConfiguration
{
    private static final String EXCHANGE = "abiquo.tracer.fanout";

    private static final String QUEUE = "abiquo.tracer.fanout.traces";

    @Override
    public AMQPFlags getFlags()
    {
        return AMQPFlags.fanout() //
            .exchangeDurable(true) //
            .queueDurable(false) //
            .queueExclusive(false) //
            .queueAutoDelete(false).build();
    }

    public static class Producer extends TraceBroadcast
    {

    }

    public static class Consumer extends TraceBroadcast
    {
        public Consumer(final String queueSuffix)
        {
            super(queueSuffix);
        }
    }

    protected TraceBroadcast()
    {
        super();
    }

    protected TraceBroadcast(final String queueSuffix)
    {
        super(QUEUE + "." + queueSuffix);
    }

    @Override
    public String getExchange()
    {
        return EXCHANGE;
    }
}
