/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

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
    public AMQPFlags getFlags()
    {
        return AMQPFlags.topic() //
            .exchangeDurable(true) //
            .queueDurable(true) //
            .queueExclusive(false) //
            .queueAutoDelete(false) //
            .build();
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
