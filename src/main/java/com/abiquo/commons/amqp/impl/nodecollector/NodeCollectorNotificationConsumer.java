/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.nodecollector;

import static com.abiquo.commons.amqp.impl.nodecollector.NodeCollectorConfiguration.NODE_COLLECTOR_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.rsmodel.amqp.nodecollector.NodeCollectorNotification;
import com.rabbitmq.client.Envelope;

public class NodeCollectorNotificationConsumer extends
    BaseConsumer<NodeCollectorNotificationCallback>
{
    public NodeCollectorNotificationConsumer()
    {
        super(new NodeCollectorConfiguration(), NODE_COLLECTOR_QUEUE);
    }

    @Override
    public void consume(final Envelope envelope, final byte[] body) throws IOException
    {
        NodeCollectorNotification notification = NodeCollectorNotification.fromByteArray(body);

        if (notification != null)
        {
            for (NodeCollectorNotificationCallback callback : callbacks)
            {
                callback.onNotification(notification);
            }

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
