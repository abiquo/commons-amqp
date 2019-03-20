/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.consumer;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.serialization.AMQPDeserializer;
import com.abiquo.commons.amqp.serialization.DefaultDeserializer;
import com.google.common.base.Objects;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.Recoverable;
import com.rabbitmq.client.RecoveryListener;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.impl.recovery.AutorecoveringChannel;

/**
 * The base consumer, it handles the creation and configuration of AMQP entities, the callback
 * collection by consumer and the retry strategy when RabbitMQ goes down.
 *
 * @param <C> the type of the objects to consume
 * @author Enric Ruiz
 */
public class AMQPConsumer<C extends Serializable> implements Closeable
{
    private final static Logger log = LoggerFactory.getLogger(AMQPConsumer.class);

    protected final AMQPConfiguration configuration;

    protected final QueueSubscriber<AMQPConsumer<C>> subscriber;

    protected final AMQPCallback<C> callback;

    protected final Class<C> messageClass;

    protected final Channel channel;

    protected final AMQPDeserializer<C> deserializer;

    protected final boolean ackAfterProcess;

    private final static String DeliveryTag = "DeliveryTag";

    private final static String Redeliver = "Redeliver";

    public AMQPConsumer(final AMQPConfiguration configuration, final Class<C> messageClass,
        final AMQPCallback<C> callback, final Channel channel)
    {
        this(configuration, messageClass, callback, channel, new DefaultDeserializer<C>());
    }

    public AMQPConsumer(final AMQPConfiguration configuration, final Class<C> messageClass,
        final AMQPCallback<C> callback, final Channel channel,
        final AMQPDeserializer<C> deserializer)
    {
        this(configuration, messageClass, callback, channel, deserializer, true);
    }

    public AMQPConsumer(final AMQPConfiguration configuration, final Class<C> messageClass,
        final AMQPCallback<C> callback, final Channel channel,
        final AMQPDeserializer<C> deserializer, final boolean ackAfterProcess)
    {
        checkNotNull(configuration, "AMQPConfiguration for an AMQPConsumer can not be null");
        checkNotNull(messageClass, "Class of message for an AMQPConsumer can not be null");
        checkNotNull(callback, "AMQPCallback for an AMQPConsumer can not be null");
        checkNotNull(channel, "Channel for an AMQPConsumer can not be null");
        checkNotNull(deserializer, "Message deserializer cannot be null");

        this.configuration = configuration;
        this.messageClass = messageClass;
        this.callback = callback;
        this.channel = channel;
        this.deserializer = deserializer;
        this.subscriber = new QueueSubscriber<>(channel, this);
        this.ackAfterProcess = ackAfterProcess;
    }

    public void start() throws IOException
    {
        log.trace("Setting Qos to {} for {}", configuration.getPrefetchCount(), this);
        channel.basicQos(configuration.getPrefetchCount());

        log.trace("Declaring exhanges for {}", this);
        configuration.declareExchanges(channel);
        log.trace("Declaring queues for {}", this);
        configuration.declareQueues(channel);

        channel.basicConsume(configuration.getQueue(), false, subscriber);
    }

    protected void consume(final Envelope envelope, final BasicProperties basicProperties,
        final byte[] body) throws IOException
    {
        checkNotNull(envelope, "Cannot consume a null envelope");
        checkNotNull(body, "Cannot consume a null body message");

        final C message = deserializer.deserialize(body, messageClass);

        if (message != null)
        {
            basicProperties.getHeaders().put(DeliveryTag, envelope.getDeliveryTag());
            basicProperties.getHeaders().put(Redeliver, envelope.isRedeliver());

            callback.process(message, basicProperties.getHeaders());
            if (ackAfterProcess)
            {
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        }
        else
        {
            log.error("Rejecting message {} and body {}", envelope, body);
            channel.basicReject(envelope.getDeliveryTag(), false);
        }
    }

    public static long getDeliveryTag(final Map<String, Object> headers)
    {
        return (long) headers.get(DeliveryTag);
    }

    public static boolean isRedeliver(final Map<String, Object> headers)
    {
        return (boolean) headers.get(Redeliver);
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
            log.error("Timeout while closing " + this, e);
            throw new ShutdownSignalException(true, true, null, channel);
        }
    }

    public void abortOnRecovery()
    {
        ((AutorecoveringChannel) channel).addRecoveryListener(new RecoveryListener()
        {
            @Override
            public void handleRecoveryStarted(final Recoverable recoverable)
            {
                // noop
            }

            @Override
            public void handleRecovery(final Recoverable recoverable)
            {
                try
                {
                    log.trace("Aborting channel {}...", this);
                    ((Channel) recoverable).abort();
                    log.trace("Aborted");
                }
                catch (ShutdownSignalException e)
                {
                    log.trace("Channel {} is already closed", this);
                }
                catch (IOException e)
                {
                    log.error("Unable to abort channel {}, please restart this abiquo " //
                        + "API instance to avoid malfunction", this);
                }
            }
        });
    }

    @Override
    public String toString()
    {
        return Objects.toStringHelper(this.getClass()).omitNullValues() //
            .addValue(configuration.toString()) //
            .add("MessageClass", messageClass.getSimpleName()) //
            .add("Channel", channel.getChannelNumber()) //
            .toString();
    }
}
