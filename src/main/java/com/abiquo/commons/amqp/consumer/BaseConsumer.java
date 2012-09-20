/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.consumer;

import static com.abiquo.commons.amqp.config.DefaultConfiguration.getHost;
import static com.abiquo.commons.amqp.util.ConsumerUtils.reconnectionExecutor;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.config.ChannelHandler;
import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.abiquo.commons.amqp.consumer.retry.DelayedRetryStrategy;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * The base consumer, it handles the creation and configuration of AMQP entities, the callback
 * collection by consumer and the retry strategy when RabbitMQ goes down.
 * 
 * @param <C> the type of the objects used as callback
 * @author Enric Ruiz
 */
public abstract class BaseConsumer<C> extends ChannelHandler
{
    private final static Logger LOGGER = LoggerFactory.getLogger(BaseConsumer.class);

    protected QueueSubscriber<BaseConsumer<C>> consumer;

    protected Set<C> callbacks;

    protected DefaultConfiguration configuration;

    protected String queueName;

    protected Class< ? extends RetryStrategy> strategyClass;

    public abstract void consume(Envelope envelope, byte[] body) throws IOException;

    public BaseConsumer(final DefaultConfiguration configuration, final String queue,
        final Class< ? extends RetryStrategy> retryStrategy)
    {
        this.callbacks = new HashSet<C>();
        this.configuration = configuration;
        this.queueName = queue;
        this.strategyClass = retryStrategy;
    }

    public BaseConsumer(final DefaultConfiguration configuration, final String queue)
    {
        this(configuration, queue, DelayedRetryStrategy.class);
    }

    public void start()
    {
        try
        {
            startConsumer();
        }
        catch (Exception e)
        {
            LOGGER.error("Unable to connect to {}", getHost());
            reconnectAsync();
        }
    }

    public void stop() throws IOException
    {
        stopConsumer();
    }

    @Override
    public void shutdownCompleted(final ShutdownSignalException cause)
    {
        LOGGER.error("Connection lost to {}", getHost());
        reconnect();
    }

    public void addCallback(final C callback)
    {
        callbacks.add(callback);
    }

    public int getPrefetchCount()
    {
        return 1;
    }

    private void startConsumer() throws IOException
    {
        openChannelAndConnection();
        getChannel().basicQos(getPrefetchCount());

        configuration.declareExchanges(getChannel());
        configuration.declareQueues(getChannel());

        consumer = new QueueSubscriber<BaseConsumer<C>>(getChannel(), this);
        getChannel().basicConsume(queueName, false, consumer);
    }

    private void stopConsumer() throws IOException
    {
        getChannel().basicCancel(consumer.getConsumerTag());
        closeChannelAndConnection();
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

    private void reconnect()
    {
        try
        {
            RetryStrategy strategy = strategyClass.newInstance();

            while (strategy.shouldRetry())
            {
                LOGGER.debug("Try to reconnect to {}", getHost());

                try
                {
                    startConsumer();
                    LOGGER.debug("And we are back!");
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
            LOGGER.debug("Unable to instance new retry strategy");
        }

        LOGGER.debug("Unable to reconnect to {}", getHost());
    }
}
