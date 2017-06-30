/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

public class SyncRemoteServiceConsumersConfiguration extends AMQPConfiguration
{
    private static final String SYNCH_RS_CONFIG_EXCHANGE = "abiquo.api.synchrs";

    private static final String SYNCH_RS_REQUESTS_QUEUE = "abiquo.api.synchrs.requests";

    private static final String SYNCH_RS_REQUESTS_ROUTING_KEY = SYNCH_RS_REQUESTS_QUEUE;

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
        return SYNCH_RS_CONFIG_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return SYNCH_RS_REQUESTS_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return SYNCH_RS_REQUESTS_QUEUE;
    }
}
