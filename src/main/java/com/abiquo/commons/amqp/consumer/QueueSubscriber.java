/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class QueueSubscriber<T extends AMQPConsumer< ? >> extends DefaultConsumer
{
    private final static Logger LOG = LoggerFactory.getLogger(QueueSubscriber.class);

    private T consumer;

    public QueueSubscriber(final Channel channel, final T consumer)
    {
        super(channel);
        this.consumer = consumer;
    }

    @Override
    public void handleDelivery(final String consumerTag, final Envelope envelope,
        final BasicProperties properties, final byte[] body)
    {
        try
        {
            consumer.consume(envelope, body);
        }
        catch (Throwable t)
        {
            LOG.error(
                "Unhandled exception captured, trying to reject message to prevent consumer crash",
                t);

            try
            {
                getChannel().basicReject(envelope.getDeliveryTag(), false);
            }
            catch (IOException io)
            {
                LOG.error("Unable to reject message", io);
            }
        }
    }
}
