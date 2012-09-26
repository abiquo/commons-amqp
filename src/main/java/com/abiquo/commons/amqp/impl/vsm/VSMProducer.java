/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.vsm;

import static com.abiquo.commons.amqp.impl.vsm.VSMConfiguration.VSM_EXCHANGE;
import static com.abiquo.commons.amqp.impl.vsm.VSMConfiguration.VSM_ROUTING_KEY;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import com.abiquo.commons.amqp.impl.vsm.domain.VirtualSystemEvent;
import com.abiquo.commons.amqp.producer.BaseProducer;

public class VSMProducer extends BaseProducer<VirtualSystemEvent>
{
    public VSMProducer()
    {
        super(new VSMConfiguration());
    }

    @Override
    public void publish(VirtualSystemEvent message) throws IOException
    {
        publishPersistentText(getChannel(), VSM_EXCHANGE, VSM_ROUTING_KEY, message.toByteArray());
    }
}
