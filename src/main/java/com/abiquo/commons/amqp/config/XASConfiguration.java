/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import com.abiquo.commons.amqp.AMQPConfiguration;
import com.abiquo.commons.amqp.AMQPFlags;

public class XASConfiguration
{

    public static class RequestConfiguration extends AMQPConfiguration
    {
        private static final String EXCHANGE = "abiquo.xas";

        // FIXME final String datacenterId;
        // see ServiceAsyncClientService fakeXasDatacenterId
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
            // FIXME return EXCHANGE.concat(".requests.").concat(datacenterId);
            return EXCHANGE.concat(".requests");
        }

        @Override
        public String getQueue()
        {
            return getRoutingKey();
        }
    }

    // FIXME xas - responses goes to the same queue as nars
    public static class ResponseConfiguration extends NARSConfiguration.ResponseConfiguration
    {

    }
}
