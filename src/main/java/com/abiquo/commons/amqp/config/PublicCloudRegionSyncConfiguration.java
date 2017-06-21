/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

/**
 * Common RabbitMQ Broker configuration for public cloud region synchronization consumer and
 * producer.
 * 
 * @author sergi.castro@abiquo.com
 */
public class PublicCloudRegionSyncConfiguration extends AMQPConfiguration
{
    protected static final String PCR_SYNC_EXCHANGE = "abiquo.pcrsync";

    protected static final String PCR_SYNC_ROUTING_KEY = "abiquo.pcrsync.messages";

    private static final String PCR_SYNC_QUEUE = PCR_SYNC_ROUTING_KEY;

    @Override
    public AMQPFlags getFlags()
    {
        return AMQPFlags.direct() //
            .exchangeDurable(true) //
            .queueDurable(true) //
            .queueExclusive(false) //
            .queueAutoDelete(false) //
            .build();
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
