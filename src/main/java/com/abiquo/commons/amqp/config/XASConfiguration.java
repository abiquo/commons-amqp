/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

public class XASConfiguration
{
    private static final String EXCHANGE = "abiquo.xas";

    public static class RequestConfiguration extends NARSConfiguration.RequestConfiguration
    {
        public RequestConfiguration(final String datacenterId)
        {
            super(datacenterId);
        }

        @Override
        public String getExchange()
        {
            return EXCHANGE;
        }
    }

    public static class ResponseConfiguration extends NARSConfiguration.ResponseConfiguration
    {
        @Override
        public String getExchange()
        {
            return EXCHANGE;
        }
    }
}
