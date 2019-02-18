/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

public class NARSConfiguration
{
    private static final String EXCHANGE = "abiquo.nars";

    public static class RequestConfiguration extends AMQPConfiguration
    {
        private String datacenterId;

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
            return EXCHANGE;
        }

        @Override
        public String getRoutingKey()
        {
            return EXCHANGE.concat(".requests.").concat(datacenterId);
        }

        @Override
        public String getQueue()
        {
            return getRoutingKey();
        }
    }

    public static class ResponseConfiguration extends AMQPConfiguration
    {
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
            return EXCHANGE;
        }

        @Override
        public String getRoutingKey()
        {
            return EXCHANGE.concat(".responses");
        }

        @Override
        public String getQueue()
        {
            return getRoutingKey();
        }
    }
}
