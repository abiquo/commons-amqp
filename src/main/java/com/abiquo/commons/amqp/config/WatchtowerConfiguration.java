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
 * Common RabbitMQ Broker configuration for Wathtower consumer and producer.
 * 
 * @author Ignasi Barrera
 */
public class WatchtowerConfiguration extends AMQPConfiguration
{
    private static final String WATCHTOWER_EXCHANGE = "watchtower.alarms.notification";

    private static final String WATCHTOWER_ROUTING_KEY = "watchtower.alarm.notificacion";

    private static final String WATCHTOWER_QUEUE = WATCHTOWER_ROUTING_KEY;

    @Override
    public void declareExchanges(final Channel channel) throws IOException
    {
        channel.exchangeDeclare(getExchange(), DirectExchange, Durable);
    }

    @Override
    public void declareQueues(final Channel channel) throws IOException
    {
        channel.queueDeclare(getQueue(), Durable, !Exclusive, !Autodelete, null);
        channel.queueBind(getQueue(), getExchange(), getRoutingKey());
    }

    @Override
    public String getExchange()
    {
        return WATCHTOWER_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return WATCHTOWER_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return WATCHTOWER_QUEUE;
    }
}
