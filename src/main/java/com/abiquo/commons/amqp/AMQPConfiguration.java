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

    protected static final boolean Durable = true;

    protected static final boolean Exclusive = true;

    protected static final boolean Autodelete = true;

    public abstract void declareExchanges(Channel channel) throws IOException;

    public abstract void declareQueues(Channel channel) throws IOException;

    public abstract String getExchange();

    public abstract String getRoutingKey();

    public abstract String getQueue();

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
