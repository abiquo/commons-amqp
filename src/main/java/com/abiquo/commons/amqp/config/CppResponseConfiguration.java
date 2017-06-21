/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

public class CppResponseConfiguration extends AMQPConfiguration
{
    private static final String CPP_RESPONSE_EXCHANGE = "abiquo.cpp";

    private static final String CPP_RESPONSE_ROUTING_KEY = "abiquo.cpp.notifications";

    private static final String CPP_RESPONSE_QUEUE = CPP_RESPONSE_ROUTING_KEY;

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
        return CPP_RESPONSE_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return CPP_RESPONSE_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return CPP_RESPONSE_QUEUE;
    }
}
