/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.am;

import static com.abiquo.commons.amqp.impl.am.AMConfiguration.AM_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.commons.amqp.impl.am.domain.TemplateStatusEvent;
import com.rabbitmq.client.Envelope;

public class AMConsumer extends BaseConsumer<AMCallback>
{
    public AMConsumer()
    {
        super(new AMConfiguration(), AM_QUEUE);
    }

    @Override
    public void consume(Envelope envelope, byte[] body) throws IOException
    {
        TemplateStatusEvent event = TemplateStatusEvent.fromByteArray(body);

        if (event != null)
        {
            for (AMCallback callback : callbacks)
            {

                if (event.getStatus().equalsIgnoreCase("DOWNLOADING"))
                {
                    callback.onDownloading(event);
                }
                else if (event.getStatus().equalsIgnoreCase("DOWNLOAD"))
                {
                    callback.onDownload(event);
                }
                else if (event.getStatus().equalsIgnoreCase("ERROR"))
                {
                    callback.onError(event);
                }
                else if (event.getStatus().equalsIgnoreCase("NOT_DOWNLOAD"))
                {
                    callback.onNotDownload(event);
                }
                else
                {
                    throw new IOException("Unexpected OVF Package Instance status : "
                        + event.getStatus());
                }
            }

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
