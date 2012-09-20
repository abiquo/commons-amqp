/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.ha;

import java.io.IOException;

import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Channel;

public class HAConfiguration extends DefaultConfiguration
{
    public static final String HA_EXCHANGE = "abiquo.ha";

    public static final String HA_ROUTING_KEY = "abiquo.ha.tasks";

    public static final String HA_QUEUE = HA_ROUTING_KEY;

    @Override
    public void declareExchanges(Channel channel) throws IOException
    {
        channel.exchangeDeclare(HA_EXCHANGE, DirectExchange, Durable);
    }

    @Override
    public void declareQueues(Channel channel) throws IOException
    {
        channel.queueDeclare(HA_QUEUE, Durable, NonExclusive, NonAutodelete, null);
        channel.queueBind(HA_QUEUE, HA_EXCHANGE, HA_ROUTING_KEY);
    }
}
