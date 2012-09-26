/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.bpm;

import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConfiguration.RequestType;
import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestProducer;

public class BPMRequestProducer extends DatacenterRequestProducer
{
    public BPMRequestProducer(String datacenterId)
    {
        super(datacenterId, RequestType.BPM);
    }
}
