/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.scheduler;

import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_EXCHANGE;
import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_FAST_QUEUE;
import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_REQUESTS_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.commons.amqp.scheduler.SchedulerCallback;
import com.abiquo.rsmodel.amqp.scheduler.SchedulerRequest;
import com.rabbitmq.client.Envelope;

public class SchedulerFastRouterConsumer extends BaseConsumer<SchedulerCallback>
{
    private final static Logger LOGGER = LoggerFactory.getLogger(SchedulerFastRouterConsumer.class);

    public SchedulerFastRouterConsumer()
    {
        super(new SchedulerConfiguration(), SCHEDULER_FAST_QUEUE);
    }

    @Override
    public int getPrefetchCount()
    {
        return Integer.valueOf(System.getProperty("abiquo.scheduler.fast.prefetch", "100").trim());
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

            LOGGER.debug("Forward of {} operation for virtual machine {} to scheduler", request
                .getOperation().name(), request.getVirtualMachineId());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
