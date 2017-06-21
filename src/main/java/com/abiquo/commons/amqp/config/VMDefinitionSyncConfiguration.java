/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

public class VMDefinitionSyncConfiguration extends AMQPConfiguration
{
    private static final String VM_DEF_SYNC_EXCHANGE = "abiquo.virtualmachines";

    private static final String VM_DEF_SYNC_QUEUE = "abiquo.virtualmachines.definitionsyncs";

    private static final String VM_DEF_SUNC_ROUTING_KEY = "";

    @Override
    public AMQPFlags getFlags()
    {
        return AMQPFlags.direct() //
            .exchangeDurable(true) //
            .queueDurable(true) //
            .queueExclusive(false) //
            .queueAutoDelete(false) //
            .build();
    }

    @Override
    public String getExchange()
    {
        return VM_DEF_SYNC_EXCHANGE;
    }

    @Override
    public String getRoutingKey()
    {
        return VM_DEF_SUNC_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return VM_DEF_SYNC_QUEUE;
    }
}
