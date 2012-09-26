/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter;

import static com.abiquo.commons.amqp.impl.datacenter.DatacenterNotificationConfiguration.NOTIFICATIONS_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterNotification;
import com.rabbitmq.client.Envelope;

public class DatacenterNotificationConsumer extends BaseConsumer<DatacenterNotificationCallback>
{
    public DatacenterNotificationConsumer()
    {
        super(new DatacenterNotificationConfiguration(), NOTIFICATIONS_QUEUE);
    }

    @Override
    public void consume(Envelope envelope, byte[] body) throws IOException
    {
        DatacenterNotification notification = DatacenterNotification.fromByteArray(body);

        if (notification != null)
        {
            for (DatacenterNotificationCallback callback : callbacks)
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
