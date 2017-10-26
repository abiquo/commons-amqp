/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.SSLContext;

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

    private boolean sslEnabled;
    
    private boolean trustAllCertificates;
    
    /**
     * Builds a new {@link AMQPChannelFactory} setting the virtual host specified in system property
     * {@link AMQPProperties#getVirtualHost()}
     */
    public AMQPChannelFactory()
    {
        this(AMQPProperties.getUserName(), //
            AMQPProperties.getPassword(), //
            AMQPProperties.getVirtualHost(), //
            Collections.emptyList(), //
            AMQPProperties.getNetworkRecoveryInterval(), //
            AMQPProperties.getConnectionTimeout(), //
            AMQPProperties.getRequestedHeartbeat(), //
            new SystemPropertyAddressResolver(), 
            AMQPProperties.isTLSEnabled(), //
            AMQPProperties.trustAllCertificates());
    }

    public AMQPChannelFactory(final List<RecoveryListener> recoveryListeners)
    {
        this(AMQPProperties.getUserName(), //
            AMQPProperties.getPassword(), //
            AMQPProperties.getVirtualHost(), //
            recoveryListeners, //
            AMQPProperties.getNetworkRecoveryInterval(), //
            AMQPProperties.getConnectionTimeout(), //
            AMQPProperties.getRequestedHeartbeat(), //
            new SystemPropertyAddressResolver(), //
            AMQPProperties.isTLSEnabled(), //
            AMQPProperties.trustAllCertificates());
    }

    public AMQPChannelFactory(final String virtualHost)
    {
        this(AMQPProperties.getUserName(), //
            AMQPProperties.getPassword(), //
            virtualHost, //
            Collections.emptyList(), //
            AMQPProperties.getNetworkRecoveryInterval(), //
            AMQPProperties.getConnectionTimeout(), //
            AMQPProperties.getRequestedHeartbeat(), //
            new SystemPropertyAddressResolver(), //
            AMQPProperties.isTLSEnabled(), //
            AMQPProperties.trustAllCertificates());
    }

    public AMQPChannelFactory(final String virtualHost,
        final List<RecoveryListener> recoveryListeners)
    {
        this(AMQPProperties.getUserName(), //
            AMQPProperties.getPassword(), //
            virtualHost, //
            recoveryListeners, //
            AMQPProperties.getNetworkRecoveryInterval(), //
            AMQPProperties.getConnectionTimeout(), //
            AMQPProperties.getRequestedHeartbeat(), //
            new SystemPropertyAddressResolver(), //
            AMQPProperties.isTLSEnabled(), //
            AMQPProperties.trustAllCertificates());
    }

    public AMQPChannelFactory(final String username, final String password,
        final String virtualHost, final List<RecoveryListener> recoveryListeners,
        final int networkRecoveryInterval, final int connectionTimeout,
        final int requestedHeartbeat, final AddressResolver addressResolver,
        final boolean sslEnabled, final boolean trustAllCertificates)
    {
        Objects.requireNonNull(Strings.emptyToNull(virtualHost),
            "virtualHost should not be null or empty");

        this.virtualHost = virtualHost;
        this.recoveryListeners.addAll(recoveryListeners);
        this.addressResolver = addressResolver;
        this.sslEnabled = sslEnabled;
        this.trustAllCertificates = trustAllCertificates;
        
        connectionFactory = new com.rabbitmq.client.ConnectionFactory();
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setTopologyRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(networkRecoveryInterval);
        connectionFactory.setConnectionTimeout(connectionTimeout);
        connectionFactory.setRequestedHeartbeat(requestedHeartbeat);
        connectionFactory.setExceptionHandler(new StrictExceptionHandler());
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
            initializeSSL(connectionFactory);
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

    public void initializeSSL(final ConnectionFactory connectionFactory) throws SSLException
    {
        if (!sslEnabled)
        {
            return;
        }

        try
        {
            if (trustAllCertificates)
            {
                // By default a TrustEverythingTrustManager is used
                connectionFactory.useSslProtocol();
            }
            else
            {
                connectionFactory.useSslProtocol(SSLContext.getDefault());
            }
        }
        catch (Exception e)
        {
            throw new SSLException(e);
        }
    }

    public String getVirtualHost()
    {
        return virtualHost;
    }
}
