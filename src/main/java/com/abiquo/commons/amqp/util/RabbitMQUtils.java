/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import com.abiquo.commons.amqp.AMQPProperties;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * A set of utility methods related to RabbitMQ server.
 * 
 * @author eruiz@abiquo.com
 */
public class RabbitMQUtils
{
    private static final ConnectionFactory connectionFactory = new ConnectionFactory();

    static
    {
        connectionFactory.setHost(AMQPProperties.getBrokerHost());
        connectionFactory.setPort(AMQPProperties.getBrokerPort());
        connectionFactory.setUsername(AMQPProperties.getUserName());
        connectionFactory.setPassword(AMQPProperties.getPassword());
        connectionFactory.setVirtualHost(AMQPProperties.getVirtualHost());
        connectionFactory.setConnectionTimeout(AMQPProperties.getConnectionTimeout());
        connectionFactory.setRequestedHeartbeat(AMQPProperties.getRequestedHeartbeat());
    }

    public static boolean pingRabbitMQ()
    {
        try
        {
            final Connection connection = connectionFactory.newConnection();
            try
            {
                return connection.isOpen();
            }
            finally
            {
                connection.close();
            }
        }
        catch (Throwable e)
        {
            return false;
        }
    }
}
