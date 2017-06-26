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
public abstract class TracerConfiguration extends AMQPFanoutConfiguration
{
    private static final String TRACER_EXCHANGE = "abiquo.tracer";

    private static final String TRACER_QUEUE = "abiquo.tracer.traces";

    /**
     * Faount consumers need to configure unique queue names.
     */
    public static class Consumer extends TracerConfiguration
    {
        public Consumer(final String queueSuffix)
        {
            super(queueSuffix);
        }
    }

    /**
     * Fanout producers don't need to configure the queues. They can be created without queue
     * specific information.
     */
    public static class Producer extends TracerConfiguration
    {

    }

    /**
     * Constructor to be used by producers, as they don't need to configure a queue name.
     */
    protected TracerConfiguration()
    {
        super();
    }

    /**
     * Constructor to be used by consumers, as they need to configure unique queue names.
     */
    protected TracerConfiguration(final String queueSuffix)
    {
        super(TRACER_QUEUE + "." + queueSuffix);
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
