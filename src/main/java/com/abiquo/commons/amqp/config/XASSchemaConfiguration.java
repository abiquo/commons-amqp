/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

public class XASSchemaConfiguration extends AMQPConfiguration
{
    private static final String SERVICE_SCHEMA_RESPONSE_EXCHANGE = "abiquo.xasschema";

    private static final String SERVICE_SCHEMA_RESPONSE_ROUTING_KEY = "abiquo.xasschema.updates";

    private static final String SERVICE_SCHEMA_RESPONSE_QUEUE = SERVICE_SCHEMA_RESPONSE_ROUTING_KEY;

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
        return SERVICE_SCHEMA_RESPONSE_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return SERVICE_SCHEMA_RESPONSE_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return SERVICE_SCHEMA_RESPONSE_QUEUE;
    }
}
