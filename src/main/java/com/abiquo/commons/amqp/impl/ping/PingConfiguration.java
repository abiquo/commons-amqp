/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.ping;

import java.io.IOException;

import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Channel;

public class PingConfiguration extends DefaultConfiguration
{
    @Override
    public void declareExchanges(Channel channel) throws IOException
    {
        // Intentionally empty
    }

    @Override
    public void declareQueues(Channel channel) throws IOException
    {
        // Intentionally empty
    }
}
