/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

// FIXME XAS deploy
// not in the scope of a dc (nars use datacenterUuid in the exchange names)
// see ServiceAsyncClientService fakeXasDatacenterId
// responses goes to the same queue as nars
public class XASConfiguration
{
    public static class RequestConfiguration extends AMQPConfiguration
    {
        private static final String EXCHANGE = "abiquo.xas";

        public RequestConfiguration()
        {
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
            return EXCHANGE.concat(".requests");
        }

        @Override
        public String getQueue()
        {
            return getRoutingKey();
        }
    }

    public static class ResponseConfiguration extends NARSConfiguration.ResponseConfiguration
    {
    }
}
