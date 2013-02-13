/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownListener;

public abstract class ChannelHandler implements ShutdownListener
{
    private final ConnectionFactory connectionFactory;

    private Connection connection;

    private Channel channel;

    public ChannelHandler()
    {
        connectionFactory = new com.rabbitmq.client.ConnectionFactory();

        connectionFactory.setHost(AMQPProperties.getBrokerHost());
        connectionFactory.setPort(AMQPProperties.getBrokerPort());
        connectionFactory.setUsername(AMQPProperties.getUserName());
        connectionFactory.setPassword(AMQPProperties.getPassword());
        connectionFactory.setVirtualHost(AMQPProperties.getVirtualHost());
        connectionFactory.setConnectionTimeout(AMQPProperties.getConnectionTimeout());
        connectionFactory.setRequestedHeartbeat(AMQPProperties.getRequestedHeartbeat());

        connection = null;
        channel = null;
    }

    public Channel getChannel()
    {
        return channel;
    }

    protected void openChannelAndConnection() throws IOException
    {
        if (connection == null)
        {
            connection = connectionFactory.newConnection();
            connection.addShutdownListener(this);
        }
        else if (!connection.isOpen())
        {
            connection.removeShutdownListener(this);

            connection = connectionFactory.newConnection();
            connection.addShutdownListener(this);
        }

        if (channel == null)
        {
            channel = connection.createChannel();
            channel.addShutdownListener(this);
        }
        else if (!channel.isOpen())
        {
            channel.removeShutdownListener(this);

            channel = connection.createChannel();
            channel.addShutdownListener(this);
        }
    }

    protected void closeChannelAndConnection() throws IOException
    {
        if (channel != null && channel.isOpen())
        {
            channel.removeShutdownListener(this);
            channel.close();
        }

        if (connection != null && connection.isOpen())
        {
            connection.removeShutdownListener(this);
            connection.close();
        }
    }
}
