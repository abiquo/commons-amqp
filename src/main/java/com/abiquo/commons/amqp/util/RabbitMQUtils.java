/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import java.io.IOException;

import com.abiquo.commons.amqp.impl.ping.PingProducer;

/**
 * A set of utility methods related to RabbitMQ server.
 * 
 * @author eruiz@abiquo.com
 */
public class RabbitMQUtils
{
    /**
     * Ping RabbitMQ server using the configuration in rabbitmq.properties file.
     * 
     * @return True on a successful ping. Otherwise false.
     */
    public static boolean pingRabbitMQ()
    {
        try
        {
            PingProducer ping = new PingProducer();
            ping.openChannel();
            ping.closeChannel();
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }
}
