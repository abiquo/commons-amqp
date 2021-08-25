/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

public class XASConfiguration
{
    private static final String REQUEST_EXCHANGE = "abiquo.xas";

    public static class RequestConfiguration extends NARSConfiguration.RequestConfiguration
    {
        public RequestConfiguration(final String datacenterId)
        {
            super(datacenterId);
        }

        @Override
        public String getExchange()
        {
            return REQUEST_EXCHANGE;
        }
    }

    public static class ResponseConfiguration extends NARSConfiguration.ResponseConfiguration
    {
        private static final String RESPONSE_EXCHANGE = REQUEST_EXCHANGE.concat(".asyncresponses");

        public static final String DEFAULT_ROUTING_KEY = "default";

        public ResponseConfiguration(final String routingKey)
        {
            super(routingKey);
        }

        @Override
        public String getExchange()
        {
            return RESPONSE_EXCHANGE;
        }
    }
}
