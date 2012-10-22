/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.scheduler;

import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_EXCHANGE;
import static com.abiquo.commons.amqp.impl.scheduler.SchedulerConfiguration.SCHEDULER_ROUTING_KEY;
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
        publishPersistentText(getChannel(), SCHEDULER_EXCHANGE, SCHEDULER_ROUTING_KEY,
            request.toByteArray());
    }
}
