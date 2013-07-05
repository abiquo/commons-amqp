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

public class VMDefinitionSyncConfiguration extends AMQPConfiguration
{
    private static final String VM_DEF_SYNC_EXCHANGE = "abiquo.virtualmachines";

    private static final String VM_DEF_SYNC_QUEUE = "abiquo.virtualmachines.definitionsyncs";

    private static final String VM_DEF_SUNC_ROUTING_KEY = "";

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
        return VM_DEF_SYNC_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return VM_DEF_SUNC_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return VM_DEF_SYNC_QUEUE;
    }
}
