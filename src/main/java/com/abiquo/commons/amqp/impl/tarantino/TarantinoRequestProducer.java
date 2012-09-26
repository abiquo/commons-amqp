/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino;

import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConfiguration.RequestType;
import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestProducer;

public class TarantinoRequestProducer extends DatacenterRequestProducer
{
    public TarantinoRequestProducer(String datacenterId)
    {
        super(datacenterId, RequestType.VIRTUAL_FACTORY);
    }
}
