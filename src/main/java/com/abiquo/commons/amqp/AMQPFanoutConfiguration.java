/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Objects.requireNonNull;

import java.io.IOException;

import com.google.common.base.Strings;
import com.rabbitmq.client.Channel;

/**
 * Base class for all fanout configurations.
 * <p>
 * Fanout configurations need to have one exchange with N queues, one per consumer. This class
 * enforces the creation of queues with different names in fanout configurations.
 * 
 * @author Ignasi Barrera
 */
public abstract class AMQPFanoutConfiguration extends AMQPConfiguration
{
    /** Unique name for each queue that participates in the fanout configuration. */
    private final String queueName;

    /**
     * Constructor to be used by producers, as they don't need to configure a queue name.
     */
    protected AMQPFanoutConfiguration()
    {
        this.queueName = null;
    }

    /**
     * Constructor to be used by consumers, as they need to configure unique queue names.
     */
    protected AMQPFanoutConfiguration(final String queueName)
    {
        this.queueName = requireNonNull(queueName, "queue name must not be null");
    }

    @Override
    public void declareQueues(final Channel channel) throws IOException
    {
        checkState(!Strings.isNullOrEmpty(queueName),
            "queue name must be set when configuring fanout consumers");

        super.declareQueues(channel);
    }

    @Override
    public String getRoutingKey()
    {
        return "";
    }

    @Override
    public String getQueue()
    {
        return queueName;
    }

}
