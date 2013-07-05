/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import static java.lang.Integer.parseInt;
import static java.lang.System.getProperty;

public class AMQPProperties
{
    public static String getBrokerHost()
    {

        return getProperty("abiquo.rabbitmq.host", "localhost");
    }

    public static int getBrokerPort()
    {
        return parseInt(getProperty("abiquo.rabbitmq.port", "5672"));
    }

    public static String getUserName()
    {
        return getProperty("abiquo.rabbitmq.username", "guest");
    }

    public static String getPassword()
    {
        return getProperty("abiquo.rabbitmq.password", "guest");
    }

    public static String getVirtualHost()
    {
        return getProperty("abiquo.rabbitmq.virtualHost", "/");
    }

    public static Integer getConnectionTimeout()
    {
        return parseInt(getProperty("abiquo.rabbitmq.connectionTimeout", "0"));
    }

    public static Integer getRequestedHeartbeat()
    {
        return parseInt(getProperty("abiquo.rabbitmq.requestedHeartbeat", "0"));
    }

    public static Integer getNetworkRecoveryInterval()
    {
        return parseInt(getProperty("abiquo.rabbitmq.networkRecoveryInterval", "5000"));
    }
}
