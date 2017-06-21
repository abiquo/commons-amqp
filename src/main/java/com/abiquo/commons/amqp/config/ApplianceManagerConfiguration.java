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
 * Common RabbitMQ Broker configuration for Appliance Manager consumer and producer.
 * 
 * @author eruiz@abiquo.com
 */
public class ApplianceManagerConfiguration extends AMQPConfiguration
{
    private static final String AM_EXCHANGE = "abiquo.am";

    private static final String AM_NOTIFICATIONS_QUEUE = "abiquo.am.notifications";

    private static final String AM_ROUTING_KEY = "";

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
        return AM_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return AM_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return AM_NOTIFICATIONS_QUEUE;
    }
}
