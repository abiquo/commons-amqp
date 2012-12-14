/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.scheduler;

import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_REQUESTS_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.commons.amqp.scheduler.SchedulerCallback;
import com.abiquo.rsmodel.amqp.scheduler.SchedulerRequest;
import com.rabbitmq.client.Envelope;

public class SchedulerConsumer extends BaseConsumer<SchedulerCallback>
{
    public SchedulerConsumer()
    {
        super(new SchedulerConfiguration(), SCHEDULER_REQUESTS_QUEUE);
    }

    @Override
    public void consume(final Envelope envelope, final byte[] body) throws IOException
    {
        SchedulerRequest request = SchedulerRequest.fromByteArray(body);

        if (request != null)
        {
            for (SchedulerCallback callback : callbacks)
            {
                callback.onResponse(request);
            }

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
