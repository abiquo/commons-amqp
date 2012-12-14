/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tracer;

import static com.abiquo.commons.amqp.impl.tracer.TracerConfiguration.TRACER_EXCHANGE;
import static com.abiquo.commons.amqp.impl.tracer.TracerConfiguration.TRACER_ROUTING_KEY;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import com.abiquo.commons.amqp.producer.BaseProducer;
import com.abiquo.rsmodel.amqp.tracer.Trace;

public class TracerProducer extends BaseProducer<Trace>
{
    public TracerProducer()
    {
        super(new TracerConfiguration());
    }

    @Override
    public void publish(Trace message) throws IOException
    {
        publishPersistentText(getChannel(), TRACER_EXCHANGE, TRACER_ROUTING_KEY,
            message.toByteArray());
    }
}
