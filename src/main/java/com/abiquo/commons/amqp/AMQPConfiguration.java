/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import java.io.IOException;

import com.google.common.base.Objects;
import com.rabbitmq.client.Channel;

/**
 * Generic broker configuration, each module configuration must extend this class and fill the
 * abstract methods.
 * 
 * @author eruiz@abiquo.com
 */
public abstract class AMQPConfiguration
{
    protected static final String FanoutExchange = "fanout";

    protected static final String DirectExchange = "direct";

    protected static final String TopicExchange = "topic";

    public abstract String getExchange();

    public abstract String getRoutingKey();

    public abstract String getQueue();

    public abstract AMQPFlags getFlags();

    public void declareExchanges(final Channel channel) throws IOException
    {
        AMQPFlags flags = getFlags();

        // Some configurations, such as teh PCR delayed queues don't require an exchange
        if (flags.exchangeType() != null)
        {
            channel.exchangeDeclare(getExchange(), flags.exchangeType(), flags.exchangeDurable());
        }
    }

    public void declareQueues(final Channel channel) throws IOException
    {
        AMQPFlags flags = getFlags();

        channel.queueDeclare(getQueue(), flags.queueDurable(), flags.queueExclusive(),
            flags.queueAutoDelete(), flags.queueArguments());

        // No need to bind the queue if there is no exchange
        if (flags.exchangeType() != null)
        {
            channel.queueBind(getQueue(), getExchange(), getRoutingKey());
        }
    }

    public int getPrefetchCount()
    {
        return 1;
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this.getClass()).omitNullValues() //
            .add("Exchange", getExchange()) //
            .add("RoutingKey", getRoutingKey()) //
            .add("Queue", getQueue()) //
            .toString();
    }
}
