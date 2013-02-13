/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import java.io.IOException;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.rabbitmq.client.Channel;

/**
 * Common RabbitMQ Broker configuration for VSM consumer and producer.
 * 
 * @author eruiz@abiquo.com
 */
public class MonitorManagerConfiguration extends AMQPConfiguration
{
    private static final String VSM_EXCHANGE = "abiquo.vsm";

    private static final String EVENT_SYNK_QUEUE = "abiquo.vsm.eventsynk";

    private static final String VSM_ROUTING_KEY = "";

    @Override
    public void declareExchanges(final Channel channel) throws IOException
    {
        channel.exchangeDeclare(getExchange(), FanoutExchange, Durable);
    }

    @Override
    public void declareQueues(final Channel channel) throws IOException
    {
        channel.queueDeclare(getQueue(), Durable, NonExclusive, NonAutodelete, null);
        channel.queueBind(getQueue(), getExchange(), getRoutingKey());
    }

    @Override
    public String getExchange()
    {
        return VSM_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return VSM_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return EVENT_SYNK_QUEUE;
    }
}
