/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.ha;

import static com.abiquo.commons.amqp.impl.ha.HAConfiguration.HA_EXCHANGE;
import static com.abiquo.commons.amqp.impl.ha.HAConfiguration.HA_ROUTING_KEY;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import com.abiquo.commons.amqp.producer.BaseProducer;
import com.abiquo.rsmodel.amqp.ha.HATask;

public class HAProducer extends BaseProducer<HATask>
{
    public HAProducer()
    {
        super(new HAConfiguration());
    }

    @Override
    public void publish(HATask message) throws IOException
    {
        publishPersistentText(getChannel(), HA_EXCHANGE, HA_ROUTING_KEY, message.toByteArray());
    }
}
