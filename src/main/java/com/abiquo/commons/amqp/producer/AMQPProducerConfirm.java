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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.serialization.AMQPSerializer;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;

/**
 * This {@link AMQPProducer} publish and confirm the successfully publication of messages. A
 * fallback should be specified in order to handle not confirmed messages from broker and the not
 * published messages due to connection errors.
 * <h2>Concurrency Considerations</h2>
 * <p>
 * Remember that AMQP {@link Channel} instances must not be shared between threads.
 * </p>
 * <h2>Use Considerations</h2>
 * <p>
 * The {@link Channel} instance must be configured to manage acknowledgements. See method
 * {@link Channel#confirmSelect()}
 * </p>
 * <p>
 * To successfully process all messages confirmations, your code must wait for all confirmations
 * using the {@link Channel} methods: {@link Channel#waitForConfirms()},
 * {@link Channel#waitForConfirms(long)}, {@link Channel#waitForConfirmsOrDie()} or
 * {@link Channel#waitForConfirmsOrDie(long)}
 * </p>
 *
 * @param <T> the type of the objects to publish
 */
public class AMQPProducerConfirm<T extends Serializable> extends AMQPProducer<T>
{
    private final static Logger log = LoggerFactory.getLogger(AMQPProducerConfirm.class);

    private final Map<Long, T> notConfirmedMessages = new ConcurrentHashMap<>();

    public AMQPProducerConfirm(final AMQPConfiguration configuration, final Channel channel,
        final AMQPSerializer<T> serializer, final Consumer<T> fallback)
    {
        super(configuration, channel, serializer, fallback);

        channel.clearConfirmListeners();

        channel.addConfirmListener(new ConfirmListener()
        {
            @Override
            public void handleNack(final long deliveryTag, final boolean multiple)
                throws IOException
            {
                log.trace("NACK message with delivery tag: {} multiple: {}", deliveryTag, multiple);

                List<T> unpublished = new ArrayList<>();

                unpublished.add(notConfirmedMessages.remove(deliveryTag));

                for (long i = 0; multiple && i < deliveryTag; i++)
                {
                    T message = notConfirmedMessages.remove(deliveryTag);
                    if (message != null)
                    {
                        unpublished.add(message);
                    }
                }

                unpublished.forEach(fallback);
            }

            @Override
            public void handleAck(final long deliveryTag, final boolean multiple) throws IOException
            {
                log.trace("ACK message with delivery tag: {} multiple: {}", deliveryTag, multiple);

                notConfirmedMessages.remove(deliveryTag);

                for (long i = deliveryTag - 1; multiple && notConfirmedMessages.remove(i) != null
                    && i >= 0; i--)
                {
                }
            }
        });
    }

    @Override
    public void publish(final T message) throws IOException
    {
        checkNotNull(message, "Message to publish can not be null");

        long nextSeqNo = channel.getNextPublishSeqNo();
        notConfirmedMessages.put(nextSeqNo, message);

        try
        {
            super.publish(message);
        }
        catch (Throwable throwable)
        {
            notConfirmedMessages.remove(nextSeqNo);
            propagate(throwable);
        }
    }
}
