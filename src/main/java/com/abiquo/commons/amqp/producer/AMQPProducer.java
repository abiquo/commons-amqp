/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.producer;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFanoutConfiguration;
import com.abiquo.commons.amqp.serialization.AMQPSerializer;
import com.abiquo.commons.amqp.util.JSONUtils;
import com.google.common.base.Throwables;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * The base producer, it handles the creation and configuration of: AMQP exchanges, queues and
 * bindings. Optionally a fallback can be specified to handle the not published messages due to
 * connection errors.
 * <h2>Concurrency Considerations</h2>
 * <p>
 * Remember that AMQP {@link Channel} instances must not be shared between threads.
 * </p>
 *
 * @param <T> the type of the objects to publish
 */
public class AMQPProducer<T> implements AutoCloseable
{
    private static final Logger log = LoggerFactory.getLogger(AMQPProducer.class);

    protected final AMQPConfiguration configuration;

    protected Channel channel;

    protected final AMQPSerializer<T> serializer;

    protected boolean declareExchanges = true;

    protected final Consumer<T> notPublishedMessagesFallback;

    protected final Consumer<T> NOOP = message -> {
    };

    /** Content-type "text/plain", deliveryMode 2 (persistent), priority zero, empty headers map */
    protected static final Function<Map<String, Object>, BasicProperties> PersistentTextPlainWithHeaders =
        headers -> new BasicProperties("text/plain", null, headers, 2, 0, null, null, null, null,
            null, null, null, null, null);

    public AMQPProducer(final AMQPConfiguration configuration, final Channel channel)
    {
        this(configuration, channel, JSONUtils.DEFAULT_SERIALIZER(), null);
    }

    public AMQPProducer(final AMQPConfiguration configuration, final Channel channel,
        final AMQPSerializer<T> serializer)
    {
        this(configuration, channel, serializer, null);
    }

    public AMQPProducer(final AMQPConfiguration configuration, final Channel channel,
        final Consumer<T> fallback)
    {
        this(configuration, channel, JSONUtils.DEFAULT_SERIALIZER(), fallback);
    }

    public AMQPProducer(final AMQPConfiguration configuration, final Channel channel,
        final AMQPSerializer<T> serializer, final Consumer<T> fallback)
    {
        checkNotNull(configuration, "AMQPConfiguration for an AMQPProducer cannot be null");
        checkNotNull(channel, "Channel for an AMQPProducer cannot be null");
        checkNotNull(serializer, "Message serializer cannot be null");

        this.configuration = configuration;
        this.channel = channel;
        this.serializer = serializer;
        this.notPublishedMessagesFallback = Optional.ofNullable(fallback).orElse(NOOP);
    }

    public void publish(final T message) throws IOException
    {
        publish(message, Collections.emptyMap());
    }

    public void publish(final T message, final Map<String, Object> headers) throws IOException
    {
        checkNotNull(message, "Message to publish can not be null");

        byte[] serialization = null;

        try
        {
            serialization = serializer.serialize(message);
        }
        catch (Throwable throwable)
        {
            notPublishedMessagesFallback.accept(message);
            propagate(throwable);
        }

        publish(message, serialization, headers);
    }

    public void publish(final T message, final byte[] messageSerialization,
        final Map<String, Object> headers) throws IOException
    {
        try
        {
            if (declareExchanges)
            {
                log.trace("Declaring exchanges for {}", this);
                configuration.declareExchanges(channel);

                if (!AMQPFanoutConfiguration.class.isAssignableFrom(configuration.getClass()))
                {
                    log.trace("Declaring queues for {}", this);
                    configuration.declareQueues(channel);
                }

                declareExchanges = false;
            }

            BasicProperties properties = null;
            if (headers != null && !headers.isEmpty())
            {
                properties = PersistentTextPlainWithHeaders.apply(headers);
            }
            else
            {
                properties = MessageProperties.PERSISTENT_TEXT_PLAIN;
            }

            channel.basicPublish(configuration.getExchange(), configuration.getRoutingKey(),
                properties, messageSerialization);
        }
        catch (Throwable throwable)
        {
            notPublishedMessagesFallback.accept(message);
            propagate(throwable);
        }
    }

    protected void propagate(final Throwable throwable) throws IOException
    {
        Throwables.propagateIfInstanceOf(throwable, IOException.class);
        Throwables.propagate(throwable);
    }

    @Override
    public void close() throws IOException
    {
        try
        {
            log.trace("Trying to close {}", this);
            channel.close();
            log.trace("{} closed", this);
        }
        catch (TimeoutException e)
        {
            log.error(String.format("Timeout while closing %s", this), e);
            throw new ShutdownSignalException(true, true, null, channel);
        }
    }

    public void setChannel(final Channel channel)
    {
        this.channel = channel;
    }

    @Override
    public String toString()
    {
        return "AMQPProducer ["
            + (configuration != null ? "configuration=" + configuration + ", " : "")
            + (channel != null ? "channel=" + channel + ", " : "")
            + (serializer != null ? "serializer=" + serializer + ", " : "") + "declareExchanges="
            + declareExchanges + ", " + (notPublishedMessagesFallback != null
                ? "notPublishedMessagesFallback=" + notPublishedMessagesFallback : "")
            + "]";
    }
}
