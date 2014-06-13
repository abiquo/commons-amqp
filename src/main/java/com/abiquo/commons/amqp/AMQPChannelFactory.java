/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import java.io.Closeable;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;

public class AMQPChannelFactory implements Closeable
{
    private final static Logger log = LoggerFactory.getLogger(AMQPChannelFactory.class);

    private final ConnectionFactory connectionFactory;

    private Connection connection = null;

    public AMQPChannelFactory()
    {
        connectionFactory = new com.rabbitmq.client.ConnectionFactory();
        connectionFactory.setHost(AMQPProperties.getBrokerHost());
        connectionFactory.setPort(AMQPProperties.getBrokerPort());
        connectionFactory.setUsername(AMQPProperties.getUserName());
        connectionFactory.setPassword(AMQPProperties.getPassword());
        connectionFactory.setVirtualHost(AMQPProperties.getVirtualHost());
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setTopologyRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(AMQPProperties.getNetworkRecoveryInterval());
        connectionFactory.setConnectionTimeout(AMQPProperties.getConnectionTimeout());
        connectionFactory.setRequestedHeartbeat(AMQPProperties.getRequestedHeartbeat());
    }

    public Channel createChannel() throws IOException
    {
        final Channel channel = newChannel();

        channel.addShutdownListener(new ShutdownListener()
        {
            @Override
            public void shutdownCompleted(final ShutdownSignalException cause)
            {
                if (!cause.isInitiatedByApplication())
                {
                    log.error("Channel number {} was closed unexpectedly. {}",
                        channel.getChannelNumber(), cause.getReason());
                }
            }
        });

        log.debug("Channel number {} created", channel.getChannelNumber());
        return channel;
    }

    @Override
    public void close() throws IOException
    {
        if (connection != null)
        {
            log.debug("Closing AMQP connection and all its channels");
            connection.close();
        }

        log.debug("AMQP connection closed");
    }

    private Channel newChannel() throws IOException
    {
        if (connection == null)
        {
            initializeConnection();
        }

        return connection.createChannel();
    }

    private synchronized void initializeConnection() throws IOException
    {
        if (connection == null)
        {
            connection = connectionFactory.newConnection();
            connection.addShutdownListener(new ShutdownListener()
            {
                @Override
                public void shutdownCompleted(final ShutdownSignalException cause)
                {
                    if (!cause.isInitiatedByApplication())
                    {
                        log.error("Connection was closed unexpectedly. {}", cause.getReason());
                    }
                }
            });
        }
    }
}
