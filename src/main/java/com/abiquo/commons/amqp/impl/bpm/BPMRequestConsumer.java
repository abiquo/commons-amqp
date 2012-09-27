/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.bpm;

import static com.abiquo.commons.amqp.util.ConsumerUtils.ackMessage;
import static com.abiquo.commons.amqp.util.ConsumerUtils.rejectMessage;

import java.io.IOException;

import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConfiguration.RequestType;
import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConsumer;
import com.abiquo.rsmodel.amqp.bpm.BPMRequest;
import com.rabbitmq.client.Envelope;

public class BPMRequestConsumer extends DatacenterRequestConsumer<BPMRequestCallback>
{
    public BPMRequestConsumer(final String datacenterId)
    {
        super(datacenterId, RequestType.BPM);
    }

    @Override
    public void consume(Envelope envelope, byte[] body) throws IOException
    {
        BPMRequest request = BPMRequest.fromByteArray(body);

        if (request != null)
        {
            for (BPMRequestCallback callback : callbacks)
            {
                callback.process(request);
            }

            ackMessage(getChannel(), envelope.getDeliveryTag());
        }
        else
        {
            rejectMessage(getChannel(), envelope.getDeliveryTag());
        }
    }
}
