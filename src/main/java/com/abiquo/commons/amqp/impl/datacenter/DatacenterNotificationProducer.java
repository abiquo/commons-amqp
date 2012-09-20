/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter;

import static com.abiquo.commons.amqp.impl.datacenter.DatacenterNotificationConfiguration.NOTIFICATIONS_EXCHANGE;
import static com.abiquo.commons.amqp.impl.datacenter.DatacenterNotificationConfiguration.NOTIFICATIONS_ROUTING_KEY;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterNotification;
import com.abiquo.commons.amqp.producer.BaseProducer;

public class DatacenterNotificationProducer extends BaseProducer<DatacenterNotification>
{
    public DatacenterNotificationProducer()
    {
        super(new DatacenterNotificationConfiguration());
    }

    @Override
    public void publish(DatacenterNotification message) throws IOException
    {
        publishPersistentText(getChannel(), NOTIFICATIONS_EXCHANGE, NOTIFICATIONS_ROUTING_KEY,
            message.toByteArray());
    }
}
