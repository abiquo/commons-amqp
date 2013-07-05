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

public class SchedulerConfiguration extends AMQPConfiguration
{
    private static final String SCHEDULER_EXCHANGE = "abiquo.scheduler";

    private static final String SCHEDULER_REQUESTS_QUEUE = "abiquo.scheduler.requests";

    private static final String SCHEDULER_FAST_QUEUE = "abiquo.scheduler.fast.requests";

    private static final String SCHEDULER_SLOW_QUEUE = "abiquo.scheduler.slow.requests";

    @Override
    public void declareExchanges(final Channel channel) throws IOException
    {
        channel.exchangeDeclare(getExchange(), TopicExchange, Durable);
    }

    @Override
    public void declareQueues(final Channel channel) throws IOException
    {
        // Declare the fast router queue for FREE and UPDATE requests
        channel.queueDeclare(SCHEDULER_FAST_QUEUE, Durable, !Exclusive, !Autodelete, null);
        channel.queueBind(SCHEDULER_FAST_QUEUE, SCHEDULER_EXCHANGE, SCHEDULER_FAST_QUEUE);

        // Declare the slow router queue for SCHEDULE requests
        channel.queueDeclare(SCHEDULER_SLOW_QUEUE, Durable, !Exclusive, !Autodelete, null);
        channel.queueBind(SCHEDULER_SLOW_QUEUE, SCHEDULER_EXCHANGE, SCHEDULER_SLOW_QUEUE);

        // Declare the main scheduler queue
        channel.queueDeclare(SCHEDULER_REQUESTS_QUEUE, Durable, !Exclusive, !Autodelete, null);
        channel.queueBind(SCHEDULER_REQUESTS_QUEUE, SCHEDULER_EXCHANGE, SCHEDULER_REQUESTS_QUEUE);
    }

    @Override
    public String getExchange()
    {
        return SCHEDULER_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return SCHEDULER_REQUESTS_QUEUE;
    }

    @Override
    public String getQueue()
    {
        return SCHEDULER_REQUESTS_QUEUE;
    }

    public String getFastRoutingKey()
    {
        return SCHEDULER_FAST_QUEUE;
    }

    public String getFastQueue()
    {
        return SCHEDULER_FAST_QUEUE;
    }

    public String getSlowRoutingKey()
    {
        return SCHEDULER_SLOW_QUEUE;
    }

    public String getSlowQueue()
    {
        return SCHEDULER_SLOW_QUEUE;
    }
}
