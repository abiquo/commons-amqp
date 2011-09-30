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

import com.abiquo.commons.amqp.config.ChannelHandler;
import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public abstract class BasicConsumer<T> extends ChannelHandler
{
    protected QueueSubscriber<BasicConsumer<T>> consumer;

    protected Set<T> callbacks;

    protected DefaultConfiguration configuration;

    protected String queueName;

    public BasicConsumer(DefaultConfiguration configuration, String queue)
    {
        this.callbacks = new HashSet<T>();
        this.configuration = configuration;
        this.queueName = queue;
        this.consumer = null;
    }

    public void start() throws IOException
    {
        openChannelAndConnection();
        getChannel().basicQos(getPrefetchCount());

        configuration.declareExchanges(getChannel());
        configuration.declareQueues(getChannel());

        consumer = new QueueSubscriber<BasicConsumer<T>>(getChannel(), this);
        getChannel().basicConsume(queueName, false, consumer);
    }

    public void stop() throws IOException
    {
        getChannel().basicCancel(consumer.getConsumerTag());
        closeChannelAndConnection();
    }

    public void addCallback(T callback)
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
        // TODO
    }

    public abstract void consume(Envelope envelope, byte[] body) throws IOException;
}
