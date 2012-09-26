/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.am;

import static com.abiquo.commons.amqp.impl.am.AMConfiguration.AM_EXCHANGE;
import static com.abiquo.commons.amqp.impl.am.AMConfiguration.AM_ROUTING_KEY;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import com.abiquo.commons.amqp.impl.am.domain.AMResponse;
import com.abiquo.commons.amqp.producer.BaseProducer;

public class AMProducer extends BaseProducer<AMResponse>
{
    public AMProducer()
    {
        super(new AMConfiguration());
    }

    @Override
    public void publish(final AMResponse response) throws IOException
    {
        publishPersistentText(getChannel(), AM_EXCHANGE, AM_ROUTING_KEY, response.toByteArray());
    }
}
