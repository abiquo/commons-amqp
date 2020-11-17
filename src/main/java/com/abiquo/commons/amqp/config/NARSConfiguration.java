/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

public class NARSConfiguration
{
    private NARSConfiguration()
    {
    }

    private static final String REQUEST_EXCHANGE = "abiquo.nars";

    public static class RequestConfiguration extends AMQPConfiguration
    {
        private final String datacenterId;

        public RequestConfiguration(final String datacenterId)
        {
            this.datacenterId = datacenterId;
        }

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
            return REQUEST_EXCHANGE;
        }

        @Override
        public String getRoutingKey()
        {
            return getExchange().concat(".requests.").concat(datacenterId);
        }

        @Override
        public String getQueue()
        {
            return getRoutingKey();
        }
    }

    private static final String RESPONSE_EXCHANGE = REQUEST_EXCHANGE.concat(".asyncresponses");

    public static final String DEFAULT_ROUTING_KEY = "default";

    public static final String BILLING_ROUTING_KEY = "billing";

    public static class ResponseConfiguration extends AMQPConfiguration
    {
        private final String routingKey;

        public ResponseConfiguration(final String routingKey)
        {
            checkArgument(!isNullOrEmpty(routingKey),
                "NARS response consumer requires a non null and non empty routing key");
            this.routingKey = routingKey;
        }

        @Override
        public AMQPFlags getFlags()
        {
            return AMQPFlags.topic() //
                .exchangeDurable(true) //
                .queueDurable(true) //
                .queueExclusive(false) //
                .queueAutoDelete(false) //
                .build();
        }

        @Override
        public String getExchange()
        {
            return RESPONSE_EXCHANGE;
        }

        @Override
        public String getRoutingKey()
        {
            return String.format("%s.%s", getExchange(), routingKey);
        }

        @Override
        public String getQueue()
        {
            return getRoutingKey();
        }
    }
}
