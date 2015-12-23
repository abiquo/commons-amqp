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
 * Common RabbitMQ Broker configuration for virtual appliance spec materialization consumer and
 * producer.
 * 
 * @author sergi.castro@abiquo.com
 */
public class VirtualApplianceSpecConfiguration extends AMQPConfiguration
{
    protected static final String VAPP_SPEC_EXCHANGE = "abiquo.vappspec";

    protected static final String VAPP_SPEC_ROUTING_KEY = "abiquo.vappspec.messages";

    private static final String VAPP_SPEC_QUEUE = VAPP_SPEC_ROUTING_KEY;

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
        return VAPP_SPEC_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return VAPP_SPEC_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return VAPP_SPEC_QUEUE;
    }

}
