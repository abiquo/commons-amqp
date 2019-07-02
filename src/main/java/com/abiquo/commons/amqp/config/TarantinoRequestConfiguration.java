/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;

public class TarantinoRequestConfiguration extends DatacenterRequestConfiguration
{
    private static final String TARANTINO_ROUTING_KEY = "virtualfactory";

    public TarantinoRequestConfiguration(final String datacenterId)
    {
        super(datacenterId, TARANTINO_ROUTING_KEY);
    }

    /**
     * qos "quality of service", or prefetch count. The number of non-acknowledged messages a
     * channel can receive. If set to one then the consumer using this channel will not receive
     * another message until it has acknowledged or rejected its current message. This feature is
     * commonly used as a load-balancing strategy using multiple consumers and a shared queue.
     */
    private static final Integer DATACENTER_REQUEST_CONSUMER_PREFETCH =
        parseInt(getProperty("abiquo.virtualfactory.prefetch", "50"));

    @Override
    public int getPrefetchCount()
    {
        return DATACENTER_REQUEST_CONSUMER_PREFETCH;
    }
}
