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

    public static final String SCHEDULER_REQUESTS_QUEUE = "abiquo.scheduler.requests";

    public static final String SCHEDULER_FAST_QUEUE = "abiquo.scheduler.fast.requests";

    public static final String SCHEDULER_SLOW_QUEUE = "abiquo.scheduler.slow.requests";

    @Override
    public void declareExchanges(final Channel channel) throws IOException
    {
        channel.exchangeDeclare(SCHEDULER_EXCHANGE, TopicExchange, Durable);
    }

    @Override
    public void declareQueues(final Channel channel) throws IOException
    {
        // Declare the fast router queue for FREE and UPDATE requests
        channel.queueDeclare(SCHEDULER_FAST_QUEUE, Durable, NonExclusive, NonAutodelete, null);
        channel.queueBind(SCHEDULER_FAST_QUEUE, SCHEDULER_EXCHANGE, SCHEDULER_FAST_QUEUE);

        // Declare the slow router queue for SCHEDULE requests
        channel.queueDeclare(SCHEDULER_SLOW_QUEUE, Durable, NonExclusive, NonAutodelete, null);
        channel.queueBind(SCHEDULER_SLOW_QUEUE, SCHEDULER_EXCHANGE, SCHEDULER_SLOW_QUEUE);

        // Declare the main scheduler queue
        channel.queueDeclare(SCHEDULER_REQUESTS_QUEUE, Durable, NonExclusive, NonAutodelete, null);
        channel.queueBind(SCHEDULER_REQUESTS_QUEUE, SCHEDULER_EXCHANGE, SCHEDULER_REQUESTS_QUEUE);
    }
}
