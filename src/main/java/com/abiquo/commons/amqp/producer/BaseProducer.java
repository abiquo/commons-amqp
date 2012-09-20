/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.producer;

import java.io.IOException;

import com.abiquo.commons.amqp.config.ChannelHandler;
import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.abiquo.commons.amqp.domain.Queuable;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * The base producer, it handles the creation and configuration of AMQP entities and the connection
 * and disconnection to RabbitMQ.
 * 
 * @param <T> the type of the objects to publish
 * @author Enric Ruiz
 */
public abstract class BaseProducer<T extends Queuable> extends ChannelHandler
{
    protected DefaultConfiguration configuration;

    public BaseProducer(DefaultConfiguration configuration)
    {
        this.configuration = configuration;
    }

    public void openChannel() throws IOException
    {
        openChannelAndConnection();
        configuration.declareExchanges(getChannel());
    }

    public void closeChannel() throws IOException
    {
        closeChannelAndConnection();
    }

    @Override
    public void shutdownCompleted(ShutdownSignalException cause)
    {
        // Empty
    }

    public abstract void publish(final T message) throws IOException;
}
