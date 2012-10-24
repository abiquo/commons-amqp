/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.scheduler;

import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_EXCHANGE;
import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_FAST_QUEUE;
import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_SLOW_QUEUE;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import com.abiquo.commons.amqp.producer.BaseProducer;
import com.abiquo.rsmodel.amqp.scheduler.SchedulerRequest;

public class SchedulerProducer extends BaseProducer<SchedulerRequest>
{
    public SchedulerProducer()
    {
        super(new SchedulerConfiguration());
    }

    @Override
    public void publish(final SchedulerRequest request) throws IOException
    {
        switch (request.getOperation())
        {
            case SCHEDULE:
                publishPersistentText(getChannel(), SCHEDULER_EXCHANGE, SCHEDULER_SLOW_QUEUE,
                    request.toByteArray());
                break;

            case FREE:
            case UPDATE:
                publishPersistentText(getChannel(), SCHEDULER_EXCHANGE, SCHEDULER_FAST_QUEUE,
                    request.toByteArray());
                break;
        }
    }
}
