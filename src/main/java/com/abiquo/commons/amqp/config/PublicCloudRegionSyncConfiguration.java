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
 * Common RabbitMQ Broker configuration for public cloud region synchronization consumer and
 * producer.
 * 
 * @author sergi.castro@abiquo.com
 */
public class PublicCloudRegionSyncConfiguration extends AMQPConfiguration
{
    private static final String PCR_SYNC_EXCHANGE = "abiquo.pcrsync";

    private static final String PCR_SYNC_ROUTING_KEY = "abiquo.pcrsync.messages";

    private static final String PCR_SYNC_QUEUE = PCR_SYNC_ROUTING_KEY;

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
        return PCR_SYNC_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return PCR_SYNC_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return PCR_SYNC_QUEUE;
    }

}
