/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.am;

import java.io.IOException;

import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Channel;

public class AMConfiguration extends DefaultConfiguration
{
    public static final String AM_EXCHANGE = "abiquo.am";

    public static final String AM_ROUTING_KEY = "abiquo.am.downloads";

    public static final String AM_QUEUE = AM_ROUTING_KEY;

    @Override
    public void declareExchanges(Channel channel) throws IOException
    {
        channel.exchangeDeclare(AM_EXCHANGE, DirectExchange, Durable);
    }

    @Override
    public void declareQueues(Channel channel) throws IOException
    {
        channel.queueDeclare(AM_QUEUE, Durable, NonExclusive, NonAutodelete, null);
        channel.queueBind(AM_QUEUE, AM_EXCHANGE, AM_ROUTING_KEY);
    }
}
