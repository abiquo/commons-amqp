/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter;

import static com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConfiguration.buildJobsQueue;

import com.abiquo.commons.amqp.consumer.BaseConsumer;
import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConfiguration.RequestType;

/**
 * An abstract {@link BaseConsumer} for manage all incoming request in a datacenter. See
 * {@link DatacenterRequestConfiguration} for configuration details.
 * 
 * @param <C> The callback type to be invoked
 * @author Enric Ruiz
 */
public abstract class DatacenterRequestConsumer<C> extends BaseConsumer<C>
{
    public DatacenterRequestConsumer(String datacenterId, RequestType type)
    {
        super(new DatacenterRequestConfiguration(datacenterId, type), buildJobsQueue(datacenterId,
            type));
    }
}
