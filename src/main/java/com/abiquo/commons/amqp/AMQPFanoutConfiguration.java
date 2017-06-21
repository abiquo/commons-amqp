/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import java.util.UUID;

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

    public AMQPFanoutConfiguration(final String baseQueueName)
    {
        this.queueName = baseQueueName + "." + UUID.randomUUID().toString();
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
