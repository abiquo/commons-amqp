/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.vsm;

import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.rsmodel.amqp.vsm.VirtualSystemEvent;
import com.rabbitmq.client.Envelope;

public class VSMConsumer extends BaseConsumer<VSMCallback>
{
    public VSMConsumer(String queue)
    {
        super(new VSMConfiguration(), queue);
    }

    @Override
    public void consume(Envelope envelope, byte[] body) throws IOException
    {
        VirtualSystemEvent event = VirtualSystemEvent.fromByteArray(body);

        if (event != null)
        {
            for (VSMCallback callback : callbacks)
            {
                callback.onEvent(event);
            }

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
