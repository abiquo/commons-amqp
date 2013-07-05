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
 * Common RabbitMQ Broker configuration for Appliance Manager consumer and producer.
 * 
 * @author eruiz@abiquo.com
 */
public class ApplianceManagerConfiguration extends AMQPConfiguration
{
    private static final String AM_EXCHANGE = "abiquo.am";

    private static final String AM_NOTIFICATIONS_QUEUE = "abiquo.am.notifications";

    private static final String AM_ROUTING_KEY = "";

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
        return AM_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return AM_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return AM_NOTIFICATIONS_QUEUE;
    }
}
