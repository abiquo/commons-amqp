/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.nodecollector;

import java.io.IOException;

import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Channel;

/**
 * Common RabbitMQ Broker configuration for node collector consumer and producer.
 * 
 * @author eruiz@abiquo.com
 */
public class NodeCollectorConfiguration extends DefaultConfiguration
{
    public static final String NODE_COLLECTOR_EXCHANGE = "abiquo.nodecollector";

    public static final String NODE_COLLECTOR_ROUTING_KEY = "abiquo.nodecollector.notifications";

    public static final String NODE_COLLECTOR_QUEUE = NODE_COLLECTOR_ROUTING_KEY;

    @Override
    public void declareExchanges(Channel channel) throws IOException
    {
        channel.exchangeDeclare(NODE_COLLECTOR_EXCHANGE, DirectExchange, Durable);
    }

    @Override
    public void declareQueues(Channel channel) throws IOException
    {
        channel.queueDeclare(NODE_COLLECTOR_QUEUE, Durable, NonExclusive, NonAutodelete, null);
        channel
            .queueBind(NODE_COLLECTOR_QUEUE, NODE_COLLECTOR_EXCHANGE, NODE_COLLECTOR_ROUTING_KEY);
    }
}
