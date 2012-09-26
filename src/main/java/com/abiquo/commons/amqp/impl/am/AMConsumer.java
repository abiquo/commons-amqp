/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.am;

import static com.abiquo.commons.amqp.impl.am.AMConfiguration.AM_NOTIFICATIONS_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.commons.amqp.impl.am.domain.AMResponse;
import com.rabbitmq.client.Envelope;

public class AMConsumer extends BaseConsumer<AMCallback>
{
    public AMConsumer()
    {
        super(new AMConfiguration(), AM_NOTIFICATIONS_QUEUE);
    }

    @Override
    public void consume(final Envelope envelope, final byte[] body) throws IOException
    {
        AMResponse event = AMResponse.fromByteArray(body);

        if (event != null)
        {
            for (AMCallback callback : callbacks)
            {
                callback.onResponse(event);
            }

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
