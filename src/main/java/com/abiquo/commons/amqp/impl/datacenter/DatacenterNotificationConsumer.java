/**
 * Abiquo community edition
 * cloud management application for hybrid clouds
 * Copyright (C) 2008-2010 - Abiquo Holdings S.L.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package com.abiquo.commons.amqp.impl.datacenter;

import static com.abiquo.commons.amqp.impl.datacenter.DatacenterNotificationConfiguration.NOTIFICATIONS_QUEUE;
import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterNotification;
import com.abiquo.commons.amqp.impl.tarantino.domain.dto.TarantinoResponse;
import com.rabbitmq.client.Envelope;

public class DatacenterNotificationConsumer extends BaseConsumer<DatacenterNotificationCallback>
{
    public DatacenterNotificationConsumer()
    {
        super(new DatacenterNotificationConfiguration(), NOTIFICATIONS_QUEUE);
    }

    @Override
    public void consume(Envelope envelope, byte[] body) throws IOException
    {
        DatacenterNotification notification = DatacenterNotification.fromByteArray(body);

        if (notification != null)
        {
            for (DatacenterNotificationCallback callback : callbacks)
            {
                callback.onNotification(notification);
            }

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
