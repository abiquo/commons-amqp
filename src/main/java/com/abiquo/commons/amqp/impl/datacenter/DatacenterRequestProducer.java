/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter;

import static com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConfiguration.buildJobsRoutingKey;
import static com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConfiguration.getDatacenterExchange;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConfiguration.RequestType;
import com.abiquo.commons.amqp.producer.BaseProducer;
import com.abiquo.rsmodel.amqp.datacenter.DatacenterRequest;

public class DatacenterRequestProducer extends BaseProducer<DatacenterRequest>
{
    private String datacenterId;

    private RequestType type;

    public DatacenterRequestProducer(final String datacenterId, final RequestType type)
    {
        super(new DatacenterRequestConfiguration(datacenterId, type));
        this.datacenterId = datacenterId;
        this.type = type;
    }

    @Override
    public void publish(DatacenterRequest message) throws IOException
    {
        publishPersistentText(getChannel(), getDatacenterExchange(),
            buildJobsRoutingKey(datacenterId, type), message.toByteArray());
    }
}
