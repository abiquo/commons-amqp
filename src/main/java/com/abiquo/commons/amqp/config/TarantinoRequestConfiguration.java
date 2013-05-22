/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

public class TarantinoRequestConfiguration extends DatacenterRequestConfiguration
{
    private static final String TARANTINO_ROUTING_KEY = "virtualfactory";

    public TarantinoRequestConfiguration(final String datacenterId)
    {
        super(datacenterId, TARANTINO_ROUTING_KEY);
    }
}
