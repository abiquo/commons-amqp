/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.vsm;

import java.io.IOException;

import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Channel;

/**
 * Common RabbitMQ Broker configuration for VSM consumer and producer.
 * 
 * @author eruiz@abiquo.com
 */
public class VSMConfiguration extends DefaultConfiguration
{
    public static final String VSM_EXCHANGE = "abiquo.vsm";

    public static final String EVENT_SYNK_QUEUE = "abiquo.vsm.eventsynk";

    public static final String VSM_ROUTING_KEY = "";

    @Override
    public void declareExchanges(Channel channel) throws IOException
    {
        channel.exchangeDeclare(VSM_EXCHANGE, FanoutExchange, Durable);
    }

    @Override
    public void declareQueues(Channel channel) throws IOException
    {
        channel.queueDeclare(EVENT_SYNK_QUEUE, Durable, NonExclusive, NonAutodelete, null);
        channel.queueBind(EVENT_SYNK_QUEUE, VSM_EXCHANGE, VSM_ROUTING_KEY);
    }
}
