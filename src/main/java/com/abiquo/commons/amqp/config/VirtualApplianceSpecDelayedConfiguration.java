/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import static com.abiquo.commons.amqp.config.VirtualApplianceSpecConfiguration.VAPP_SPEC_EXCHANGE;
import static com.abiquo.commons.amqp.config.VirtualApplianceSpecConfiguration.VAPP_SPEC_ROUTING_KEY;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.rabbitmq.client.Channel;

/**
 * Delayed queue to {@link VirutalApplianceSpecConfiguration}
 * <p>
 * This queue doesn't have any exchange or consumer
 * http://blog.james-carr.org/2012/03/30/rabbitmq-sending-a-message-to-be-consumed-later/
 */
public class VirtualApplianceSpecDelayedConfiguration extends AMQPConfiguration
{
    private static final String DELAYED_VAPP_SPEC_ROUTING_KEY =
        "abiquo.vappspec.parking-expect-no-consumers";

    private static final String DELAYED_VAPP_SPEC_QUEUE = DELAYED_VAPP_SPEC_ROUTING_KEY;

    /** Milliseconds to wait before retry a vapp spec creation in the same virtual datacenter */
    private static final long DELAY_MS = Long.getLong("abiquo.vappspec.retrydelayms", 15_000l);

    private final static Map<String, Object> DELAYED_TO = new HashMap<String, Object>(3);
    static
    {
        DELAYED_TO.put("x-message-ttl", DELAY_MS);
        DELAYED_TO.put("x-dead-letter-exchange", VAPP_SPEC_EXCHANGE);
        DELAYED_TO.put("x-dead-letter-routing-key", VAPP_SPEC_ROUTING_KEY);
    }

    @Override
    public void declareExchanges(final Channel channel) throws IOException
    {
        // no exchange
    }

    @Override
    public void declareQueues(final Channel channel) throws IOException
    {
        channel.queueDeclare(getQueue(), Durable, !Exclusive, !Autodelete, DELAYED_TO);
    }

    @Override
    public String getExchange()
    {
        return "";
    }

    @Override
    public String getRoutingKey()
    {
        return DELAYED_VAPP_SPEC_ROUTING_KEY;
    }

    @Override
    public String getQueue()
    {
        return DELAYED_VAPP_SPEC_QUEUE;
    }
}
