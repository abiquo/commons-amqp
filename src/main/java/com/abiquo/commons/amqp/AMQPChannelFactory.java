/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import static com.abiquo.commons.amqp.AMQPSSLContext.buildSSLContext;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.exception.SSLException;
import com.abiquo.commons.amqp.util.SystemPropertyAddressResolver;
import com.google.common.base.Strings;
import com.rabbitmq.client.AddressResolver;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.RecoveryListener;
import com.rabbitmq.client.ShutdownListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.impl.StrictExceptionHandler;
import com.rabbitmq.client.impl.recovery.AutorecoveringConnection;

public class AMQPChannelFactory implements Closeable
{
    private final static Logger log = LoggerFactory.getLogger(AMQPChannelFactory.class);

    private final ConnectionFactory connectionFactory;

    private Connection connection = null;

    private String virtualHost;

    private AddressResolver addressResolver;

    private List<RecoveryListener> recoveryListeners = new ArrayList<>();

    /**
     * Builds a new {@link AMQPChannelFactory} setting the virtual host specified in system property
     * {@link AMQPProperties#getVirtualHost()}
     */
    public AMQPChannelFactory()
    {
        this(AMQPProperties.getVirtualHost(), Collections.emptyList());
    }

    public AMQPChannelFactory(final List<RecoveryListener> recoveryListeners)
    {
        this(AMQPProperties.getVirtualHost(), recoveryListeners);
    }

    public AMQPChannelFactory(final String virtualHost)
    {
        this(virtualHost, Collections.emptyList());
    }

    public AMQPChannelFactory(final String virtualHost,
        final List<RecoveryListener> recoveryListeners)
    {
        Objects.requireNonNull(Strings.emptyToNull(virtualHost),
            "virtualHost should not be null or empty");

        this.virtualHost = virtualHost;
        this.recoveryListeners.addAll(recoveryListeners);

        connectionFactory = new com.rabbitmq.client.ConnectionFactory();
        connectionFactory.setUsername(AMQPProperties.getUserName());
        connectionFactory.setPassword(AMQPProperties.getPassword());
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setTopologyRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(AMQPProperties.getNetworkRecoveryInterval());
        connectionFactory.setConnectionTimeout(AMQPProperties.getConnectionTimeout());
        connectionFactory.setRequestedHeartbeat(AMQPProperties.getRequestedHeartbeat());
        connectionFactory.setExceptionHandler(new StrictExceptionHandler());

        addressResolver = new SystemPropertyAddressResolver();
    }

    public Channel createChannel() throws SSLException, IOException, TimeoutException
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

    public void addRecoveryListener(final RecoveryListener recoveryListener) throws SSLException,
        IOException, TimeoutException
    {
        if (connection == null)
        {
            initializeConnection();
        }

        ((AutorecoveringConnection) connection).addRecoveryListener(recoveryListener);
    }

    private Channel newChannel() throws SSLException, IOException, TimeoutException
    {
        if (connection == null)
        {
            initializeConnection();
        }

        return connection.createChannel();
    }

    private synchronized void initializeConnection() throws SSLException, IOException,
        TimeoutException
    {
        if (connection == null)
        {
            buildSSLContext().ifPresent(connectionFactory::useSslProtocol);
            connection = connectionFactory.newConnection(addressResolver);
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

            recoveryListeners.forEach(((AutorecoveringConnection) connection)::addRecoveryListener);
        }
    }

    public String getVirtualHost()
    {
        return virtualHost;
    }
}
