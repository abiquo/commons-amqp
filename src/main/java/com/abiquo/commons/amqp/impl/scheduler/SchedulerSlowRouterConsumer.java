/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.scheduler;

import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_EXCHANGE;
import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_REQUESTS_QUEUE;
import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_SLOW_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.commons.amqp.scheduler.SchedulerCallback;
import com.abiquo.rsmodel.amqp.scheduler.SchedulerRequest;
import com.rabbitmq.client.Envelope;

public class SchedulerSlowRouterConsumer extends BaseConsumer<SchedulerCallback>
{
    public SchedulerSlowRouterConsumer()
    {
        super(new SchedulerConfiguration(), SCHEDULER_SLOW_QUEUE);
    }

    @Override
    public int getPrefetchCount()
    {
        return 1;
    }

    @Override
    public void consume(final Envelope envelope, final byte[] body) throws IOException
    {
        SchedulerRequest request = SchedulerRequest.fromByteArray(body);

        if (request != null)
        {
            publishPersistentText(getChannel(), SCHEDULER_EXCHANGE, SCHEDULER_REQUESTS_QUEUE,
                request.toByteArray());

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
