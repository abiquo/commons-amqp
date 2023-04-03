/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

public class AsyncPublicCloudRegionSyncConfiguration extends AMQPConfiguration
{
    private static final String EXCHANGE = "abiquo.pcrsyncasync";

    private static final String QUEUE = "abiquo.pcrsyncasync.requests";

    private static final String ROUTING_KEY = QUEUE;

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
        return EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return QUEUE;
    }
}
