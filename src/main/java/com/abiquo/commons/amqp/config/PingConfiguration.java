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

public class PingConfiguration extends AMQPConfiguration
{
    @Override
    public void declareExchanges(final Channel channel) throws IOException
    {
        // Intentionally empty
    }

    @Override
    public void declareQueues(final Channel channel) throws IOException
    {
        // Intentionally empty
    }

    @Override
    public String getExchange()
    {
        return "";
    }

    @Override
    public String getRoutingKey()
    {
        return "";
    }

    @Override
    public String getQueue()
    {
        return "";
    }
}
