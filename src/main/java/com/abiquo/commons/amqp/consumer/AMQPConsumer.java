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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.serialization.AMQPDeserializer;
import com.abiquo.commons.amqp.serialization.DefaultDeserializer;
import com.google.common.base.Objects;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;

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

    protected AMQPConfiguration configuration;

    protected QueueSubscriber<AMQPConsumer<C>> subscriber;

    protected AMQPCallback<C> callback;

    protected Class<C> messageClass;

    protected Channel channel;

    protected AMQPDeserializer<C> deserializer;

    public AMQPConsumer(final AMQPConfiguration configuration, final Class<C> messageClass,
        final AMQPCallback<C> callback, final Channel channel)
    {
        checkNotNull(configuration, "AMQPConfiguration for an AMQPConsumer can not be null");
        checkNotNull(messageClass, "Class of message for an AMQPConsumer can not be null");
        checkNotNull(callback, "AMQPCallback for an AMQPConsumer can not be null");
        checkNotNull(channel, "Channel for an AMQPConsumer can not be null");

        this.configuration = configuration;
        this.messageClass = messageClass;
        this.callback = callback;
        this.channel = channel;
        this.deserializer = new DefaultDeserializer<C>();
    }

    public AMQPConsumer(final AMQPConfiguration configuration, final Class<C> messageClass,
        final AMQPCallback<C> callback, final Channel channel,
        final AMQPDeserializer<C> deserializer)
    {
        checkNotNull(configuration, "AMQPConfiguration for an AMQPConsumer can not be null");
        checkNotNull(messageClass, "Class of message for an AMQPConsumer can not be null");
        checkNotNull(callback, "AMQPCallback for an AMQPConsumer can not be null");
        checkNotNull(channel, "Channel for an AMQPConsumer can not be null");
        checkNotNull(deserializer, "Message serializer cannot be null");

        this.configuration = configuration;
        this.messageClass = messageClass;
        this.callback = callback;
        this.channel = channel;
        this.deserializer = deserializer;
    }

    public void start() throws IOException
    {
        log.trace("Setting Qos to {} for {}", configuration.getPrefetchCount(), this);
        channel.basicQos(configuration.getPrefetchCount());

        log.trace("Declaring exhanges for {}", this);
        configuration.declareExchanges(channel);
        log.trace("Declaring queues for {}", this);
        configuration.declareQueues(channel);

        subscriber = new QueueSubscriber<AMQPConsumer<C>>(channel, this);
        channel.basicConsume(configuration.getQueue(), false, subscriber);
    }

    protected void consume(final Envelope envelope, final byte[] body) throws IOException
    {
        checkNotNull(envelope, "Cannot consume a null envelope");
        checkNotNull(body, "Cannot consume a null body message");

        final C message = deserializer.deserialize(body, messageClass);

        if (message != null)
        {
            callback.process(message);
            channel.basicAck(envelope.getDeliveryTag(), false);
        }
        else
        {
            log.error("Rejecting message {} and body {}", envelope, body);
            channel.basicReject(envelope.getDeliveryTag(), false);
        }
    }

    @Override
    public void close() throws IOException
    {
        log.trace("Trying to close {}", this);

        if (channel.isOpen())
        {
            channel.close();
            log.trace("{} closed", this);
        }
        else
        {
            log.trace("{} is already closed", this);
        }
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
