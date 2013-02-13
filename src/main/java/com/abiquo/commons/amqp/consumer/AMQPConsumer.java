/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.consumer;

import static com.abiquo.commons.amqp.AMQPProperties.getBrokerHost;
import static com.abiquo.commons.amqp.util.ConsumerUtils.reconnectionExecutor;

import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.ChannelHandler;
import com.abiquo.commons.amqp.consumer.retry.DelayedRetryStrategy;
import com.abiquo.commons.amqp.util.JSONUtils;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * The base consumer, it handles the creation and configuration of AMQP entities, the callback
 * collection by consumer and the retry strategy when RabbitMQ goes down.
 * 
 * @param <C> the type of the objects used as callback
 * @author Enric Ruiz
 */
public class AMQPConsumer<T extends Serializable> extends ChannelHandler
{
    private final static Logger LOG = LoggerFactory.getLogger(AMQPConsumer.class);

    private AMQPConfiguration configuration;

    private QueueSubscriber<AMQPConsumer<T>> subscriber;

    private AMQPCallback<T> callback;

    private Class< ? extends RetryStrategy> strategyClass;

    private Class<T> messageClass;

    public static <S extends Serializable> AMQPConsumer<S> of(
        final AMQPConfiguration configuration, final Class<S> messageClass,
        final AMQPCallback<S> callback)
    {
        return new AMQPConsumer<S>(configuration, messageClass, callback);
    }

    public AMQPConsumer(final AMQPConfiguration configuration, final Class<T> messageClass,
        final AMQPCallback<T> callback, final Class< ? extends RetryStrategy> retryStrategy)
    {
        this.configuration = configuration;
        this.messageClass = messageClass;
        this.callback = callback;
        this.strategyClass = retryStrategy;
    }

    public AMQPConsumer(final AMQPConfiguration configuration, final Class<T> messageClass,
        final AMQPCallback<T> callback)
    {
        this(configuration, messageClass, callback, DelayedRetryStrategy.class);
    }

    public void consume(final Envelope envelope, final byte[] body) throws IOException
    {
        T message = JSONUtils.deserialize(body, messageClass);

        if (message != null)
        {
            callback.process(message);
            getChannel().basicAck(envelope.getDeliveryTag(), false);
        }
        else
        {
            getChannel().basicReject(envelope.getDeliveryTag(), false);
        }
    }

    public void start()
    {
        try
        {
            startConsumer();
        }
        catch (Exception e)
        {
            LOG.error("Unable to connect to {}", getBrokerHost());
            reconnectAsync();
        }
    }

    private void startConsumer() throws IOException
    {
        openChannelAndConnection();
        getChannel().basicQos(getPrefetchCount());

        configuration.declareExchanges(getChannel());
        configuration.declareQueues(getChannel());

        subscriber = new QueueSubscriber<AMQPConsumer<T>>(getChannel(), this);
        getChannel().basicConsume(configuration.getQueue(), false, subscriber);
    }

    public void stop() throws IOException
    {
        stopConsumer();
    }

    private void stopConsumer() throws IOException
    {
        getChannel().basicCancel(subscriber.getConsumerTag());
        closeChannelAndConnection();
    }

    public int getPrefetchCount()
    {
        return 1;
    }

    @Override
    public void shutdownCompleted(final ShutdownSignalException cause)
    {
        LOG.error("Connection lost to {}", getBrokerHost());
        reconnect();
    }

    private void reconnect()
    {
        try
        {
            RetryStrategy strategy = strategyClass.newInstance();

            while (strategy.shouldRetry())
            {
                LOG.debug("Try to reconnect to {}", getBrokerHost());

                try
                {
                    startConsumer();
                    LOG.debug("And we are back!");
                    return;
                }
                catch (Exception e)
                {
                    continue;
                }
            }
        }
        catch (Exception e)
        {
            LOG.debug("Unable to instance new retry strategy");
        }

        LOG.debug("Unable to reconnect to {}", getBrokerHost());
    }

    private void reconnectAsync()
    {
        reconnectionExecutor.submit(new Runnable()
        {
            @Override
            public void run()
            {
                reconnect();
            }
        });
    }
}
