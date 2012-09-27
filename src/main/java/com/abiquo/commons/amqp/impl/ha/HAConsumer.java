/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.ha;

import static com.abiquo.commons.amqp.impl.ha.HAConfiguration.HA_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.rsmodel.amqp.ha.HATask;
import com.rabbitmq.client.Envelope;

public class HAConsumer extends BaseConsumer<HACallback>
{
    public HAConsumer()
    {
        super(new HAConfiguration(), HA_QUEUE);
    }

    @Override
    public void consume(Envelope envelope, byte[] body) throws IOException
    {
        HATask task = HATask.fromByteArray(body);

        if (task != null)
        {
            for (HACallback callback : callbacks)
            {
                callback.executeHighAvailabilityTask(task);
            }

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
