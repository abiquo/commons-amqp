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

public abstract class DatacenterRequestConfiguration extends AMQPConfiguration
{
    private static final String DATACENTER_EXCHANGE = "abiquo.datacenter.requests";

    private static final String JOBS_ROUTING_KEY = "abiquo.datacenter.requests";

    private static final String JOBS_QUEUE = "abiquo.datacenter.requests";

    private String datacenterId;

    private String routingKey;

    public DatacenterRequestConfiguration(final String datacenterId, final String routingKey)
    {
        this.datacenterId = datacenterId;
        this.routingKey = routingKey;
    }

    @Override
    public void declareExchanges(final Channel channel) throws IOException
    {
        channel.exchangeDeclare(getExchange(), TopicExchange, Durable);
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
        return DATACENTER_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return JOBS_ROUTING_KEY.concat(".").concat(datacenterId).concat(".").concat(routingKey);
    }

    @Override
    public String getQueue()
    {
        return JOBS_QUEUE.concat(".").concat(datacenterId).concat(".").concat(routingKey);
    }
}
