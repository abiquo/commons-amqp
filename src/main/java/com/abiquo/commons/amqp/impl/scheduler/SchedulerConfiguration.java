/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.scheduler;

import java.io.IOException;

import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Channel;

public class SchedulerConfiguration extends DefaultConfiguration
{
    public static final String SCHEDULER_EXCHANGE = "abiquo.scheduler";

    public static final String SCHEDULER_NOTIFICATIONS_QUEUE = "abiquo.scheduler.requests";

    public static final String SCHEDULER_ROUTING_KEY = "";

    @Override
    public void declareExchanges(final Channel channel) throws IOException
    {
        channel.exchangeDeclare(SCHEDULER_EXCHANGE, DirectExchange, Durable);
    }

    @Override
    public void declareQueues(final Channel channel) throws IOException
    {
        channel.queueDeclare(SCHEDULER_NOTIFICATIONS_QUEUE, Durable, NonExclusive, NonAutodelete,
            null);
        channel.queueBind(SCHEDULER_NOTIFICATIONS_QUEUE, SCHEDULER_EXCHANGE, SCHEDULER_ROUTING_KEY);
    }
}
