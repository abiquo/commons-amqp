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

import com.abiquo.commons.amqp.util.ConsumerUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class QueueSubscriber<T extends BaseConsumer< ? >> extends DefaultConsumer
{
    private final static Logger LOGGER = LoggerFactory.getLogger(QueueSubscriber.class);

    private T consumer;

    public QueueSubscriber(Channel channel, T consumer)
    {
        super(channel);

        this.consumer = consumer;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties,
        byte[] body)
    {
        try
        {
            consumer.consume(envelope, body);
        }
        catch (Throwable t)
        {
            LOGGER.error(
                "Unhandled exception captured, trying to reject message to prevent consumer crash",
                t);

            try
            {
                ConsumerUtils.rejectMessage(getChannel(), envelope.getDeliveryTag());
            }
            catch (IOException io)
            {
                LOGGER.error("Unable to reject message", io);
            }
        }
    }
}
