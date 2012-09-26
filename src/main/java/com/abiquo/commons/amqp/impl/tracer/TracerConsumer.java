/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tracer;

import static com.abiquo.commons.amqp.impl.tracer.TracerConfiguration.TRACER_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.commons.amqp.impl.tracer.domain.Trace;
import com.rabbitmq.client.Envelope;

public class TracerConsumer extends BaseConsumer<TracerCallback>
{
    public TracerConsumer()
    {
        super(new TracerConfiguration(), TRACER_QUEUE);
    }

    @Override
    public void consume(Envelope envelope, byte[] body) throws IOException
    {
        Trace trace = Trace.fromByteArray(body);

        if (trace != null)
        {
            for (TracerCallback callback : callbacks)
            {
                callback.onTrace(trace);
            }

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
