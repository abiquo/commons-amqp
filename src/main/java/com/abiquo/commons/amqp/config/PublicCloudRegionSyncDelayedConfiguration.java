/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import static com.abiquo.commons.amqp.config.PublicCloudRegionSyncConfiguration.PCR_SYNC_EXCHANGE;
import static com.abiquo.commons.amqp.config.PublicCloudRegionSyncConfiguration.PCR_SYNC_ROUTING_KEY;

import java.util.HashMap;
import java.util.Map;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

/**
 * Delayed queue to {@link PublicCloudRegionSyncConfiguration}
 * <p>
 * This queue doesn't have any exchange or consumer
 * http://blog.james-carr.org/2012/03/30/rabbitmq-sending-a-message-to-be-consumed-later/
 */
public class PublicCloudRegionSyncDelayedConfiguration extends AMQPConfiguration
{
    private static final String DELAYED_PCR_SYNC_ROUTING_KEY =
        "abiquo.pcrsync.parking-expect-no-consumers";

    private static final String DELAYED_PCR_SYNC_QUEUE = DELAYED_PCR_SYNC_ROUTING_KEY;

    /** Milliseconds to wait before retry a PCR synchronize in the same enterprise and PCR */
    private static final long DELAY_MS = Long.getLong("abiquo.pcrsync.retrydelayms", 15_000l);

    private final static Map<String, Object> DELAYED_TO = new HashMap<String, Object>(3);
    static
    {
        DELAYED_TO.put("x-message-ttl", DELAY_MS);
        DELAYED_TO.put("x-dead-letter-exchange", PCR_SYNC_EXCHANGE);
        DELAYED_TO.put("x-dead-letter-routing-key", PCR_SYNC_ROUTING_KEY);
    }

    @Override
    public AMQPFlags getFlags()
    {
        return AMQPFlags.noExchange() //
            .queueDurable(true) //
            .queueExclusive(false) //
            .queueAutoDelete(false) //
            .queueArguments(DELAYED_TO) //
            .build();
    }

    @Override
    public String getExchange()
    {
        return "";
    }

    @Override
    public String getRoutingKey()
    {
        return DELAYED_PCR_SYNC_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return DELAYED_PCR_SYNC_QUEUE;
    }
}
