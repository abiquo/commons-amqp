/**
 * Abiquo community edition
 * cloud management application for hybrid clouds
 * Copyright (C) 2008-2010 - Abiquo Holdings S.L.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package com.abiquo.commons.amqp.consumer;

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

    public BaseConsumer(DefaultConfiguration configuration, String queue)
    {
        this.callbacks = new HashSet<C>();
        this.configuration = configuration;
        this.queueName = queue;
        this.strategyClass = DelayedRetryStrategy.class;
    }

    public BaseConsumer(DefaultConfiguration configuration, String queue,
        Class< ? extends RetryStrategy> retryStrategy)
    {
        this.callbacks = new HashSet<C>();
        this.configuration = configuration;
        this.queueName = queue;
        this.strategyClass = retryStrategy;
    }

    public void start() throws IOException
    {
        openChannelAndConnection();
        getChannel().basicQos(getPrefetchCount());

        configuration.declareExchanges(getChannel());
        configuration.declareQueues(getChannel());

        consumer = new QueueSubscriber<BaseConsumer<C>>(getChannel(), this);
        getChannel().basicConsume(queueName, false, consumer);
    }

    public void stop() throws IOException
    {
        getChannel().basicCancel(consumer.getConsumerTag());
        closeChannelAndConnection();
    }

    public void addCallback(C callback)
    {
        callbacks.add(callback);
    }

    protected int getPrefetchCount()
    {
        return 1;
    }

    @Override
    public void shutdownCompleted(ShutdownSignalException cause)
    {
        String rabbitmqHost = DefaultConfiguration.getHost();

        LOGGER.debug(String.format("Connection lost to %s", rabbitmqHost));

        try
        {
            RetryStrategy strategy = strategyClass.newInstance();

            while (strategy.shouldRetry())
            {
                LOGGER.debug(String.format("Try to reconnect to %s", rabbitmqHost));

                try
                {
                    openChannelAndConnection();
                    start();

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

        LOGGER.debug(String.format("Unable to reconnect to %s", rabbitmqHost));
    }

    public abstract void consume(Envelope envelope, byte[] body) throws IOException;
}
