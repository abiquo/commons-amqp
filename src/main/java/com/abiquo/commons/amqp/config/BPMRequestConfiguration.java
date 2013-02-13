/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

public class BPMRequestConfiguration extends DatacenterRequestConfiguration
{
    private static final String BPM_ROUTING_KEY = "bpm";

    public BPMRequestConfiguration(final String datacenterId)
    {
        super(datacenterId, BPM_ROUTING_KEY);
    }
}
