/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.producer;

import static com.google.common.base.Preconditions.checkNotNull;

import java.io.IOException;
import java.io.Serializable;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.ChannelHandler;
import com.abiquo.commons.amqp.util.JSONUtils;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * The base producer, it handles the creation and configuration of AMQP entities and the connection
 * and disconnection to RabbitMQ.
 * 
 * @param <T> the type of the objects to publish
 * @author Enric Ruiz
 */
public class AMQPProducer<T extends Serializable> extends ChannelHandler
{
    protected AMQPConfiguration configuration;

    public static <S extends Serializable> AMQPProducer<S> of(final AMQPConfiguration configuration)
    {
        return new AMQPProducer<S>(configuration);
    }

    public AMQPProducer(final AMQPConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public void shutdownCompleted(final ShutdownSignalException cause)
    {
        // Empty
    }

    public void publish(final T message) throws IOException
    {
        checkNotNull(message);

        try
        {
            // Open connection
            openChannelAndConnection();
            configuration.declareExchanges(getChannel());

            // Publish message
            getChannel().basicPublish(configuration.getExchange(), configuration.getRoutingKey(),
                MessageProperties.PERSISTENT_TEXT_PLAIN, JSONUtils.serialize(message));
        }
        finally
        {
            // Close connection
            closeChannelAndConnection();
        }
    }
}
