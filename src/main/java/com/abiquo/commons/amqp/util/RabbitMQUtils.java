/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import java.io.IOException;

import com.abiquo.commons.amqp.config.PingConfiguration;
import com.abiquo.commons.amqp.producer.AMQPProducer;

/**
 * A set of utility methods related to RabbitMQ server.
 * 
 * @author eruiz@abiquo.com
 */
// TODO
public class RabbitMQUtils
{
    private static final AMQPProducer<String> pinger = new AMQPProducer<String>(new PingConfiguration())
    {
        @Override
        public void publish(final String message) throws IOException
        {
            try
            {
                openChannelAndConnection();
            }
            finally
            {
                closeChannelAndConnection();
            }
        }
    };

    /**
     * Ping RabbitMQ server using the configuration in rabbitmq.properties file.
     * 
     * @return True on a successful ping. Otherwise false.
     */
    public static boolean pingRabbitMQ()
    {
        try
        {
            pinger.publish("");
            return true;
        }
        catch (IOException e)
        {
            return false;
        }
    }
}
