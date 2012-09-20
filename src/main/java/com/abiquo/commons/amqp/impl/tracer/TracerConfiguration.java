/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tracer;

import java.io.IOException;

import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Channel;

/**
 * Common RabbitMQ Broker configuration for Tracer consumer and producer.
 * 
 * @author eruiz@abiquo.com
 */
public class TracerConfiguration extends DefaultConfiguration
{
    public static final String TRACER_EXCHANGE = "abiquo.tracer";

    public static final String TRACER_ROUTING_KEY = "abiquo.tracer.traces";

    public static final String TRACER_QUEUE = TRACER_ROUTING_KEY;

    @Override
    public void declareExchanges(Channel channel) throws IOException
    {
        channel.exchangeDeclare(TRACER_EXCHANGE, DirectExchange, Durable);
    }

    @Override
    public void declareQueues(Channel channel) throws IOException
    {
        channel.queueDeclare(TRACER_QUEUE, Durable, NonExclusive, NonAutodelete, null);
        channel.queueBind(TRACER_QUEUE, TRACER_EXCHANGE, TRACER_ROUTING_KEY);
    }
}
