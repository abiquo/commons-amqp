/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter;

import java.io.IOException;

import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Channel;

public class DatacenterNotificationConfiguration extends DefaultConfiguration
{
    public static final String NOTIFICATIONS_EXCHANGE = "abiquo.datacenter.notifications";

    public static final String NOTIFICATIONS_ROUTING_KEY = "abiquo.datacenter.notifications";

    public static final String NOTIFICATIONS_QUEUE = NOTIFICATIONS_ROUTING_KEY;

    @Override
    public void declareExchanges(Channel channel) throws IOException
    {
        channel.exchangeDeclare(NOTIFICATIONS_EXCHANGE, DirectExchange, Durable);
    }

    @Override
    public void declareQueues(Channel channel) throws IOException
    {
        channel.queueDeclare(NOTIFICATIONS_QUEUE, Durable, NonExclusive, NonAutodelete, null);
        channel.queueBind(NOTIFICATIONS_QUEUE, NOTIFICATIONS_EXCHANGE, NOTIFICATIONS_ROUTING_KEY);
    }
}
