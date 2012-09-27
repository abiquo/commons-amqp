/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.ping;

import java.io.IOException;

import com.abiquo.commons.amqp.domain.QueuableString;
import com.abiquo.commons.amqp.producer.BaseProducer;

public class PingProducer extends BaseProducer<QueuableString>
{
    public PingProducer()
    {
        super(new PingConfiguration());
    }

    @Override
    public void publish(final QueuableString message) throws IOException
    {
        // Intentionally empty
    }
}
