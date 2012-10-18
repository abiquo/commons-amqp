/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rabbitmq.client.Channel;

/**
 * Generic broker configuration, each module configuration must extend this class and fill the
 * abstract methods.
 * 
 * @author eruiz@abiquo.com
 */
public abstract class DefaultConfiguration
{
    /** Logger **/
    private final static Logger LOGGER = LoggerFactory.getLogger(DefaultConfiguration.class);

    /** Constants **/
    protected final String FanoutExchange = "fanout";

    protected final String DirectExchange = "direct";

    protected final String TopicExchange = "topic";

    protected final boolean Durable = true;

    protected final boolean NonDurable = false;

    protected final boolean Exclusive = true;

    protected final boolean NonExclusive = false;

    protected final boolean Autodelete = true;

    protected final boolean NonAutodelete = false;

    public abstract void declareExchanges(Channel channel) throws IOException;

    public abstract void declareQueues(Channel channel) throws IOException;

    public static String getHost()
    {
        return System.getProperty("abiquo.rabbitmq.host", "localhost");
    }

    public static int getPort()
    {
        return Integer.parseInt(System.getProperty("abiquo.rabbitmq.port", "5672"));
    }

    public static String getUserName()
    {
        return System.getProperty("abiquo.rabbitmq.username", "guest");
    }

    public static String getPassword()
    {
        return System.getProperty("abiquo.rabbitmq.password", "guest");
    }

    public static String getVirtualHost()
    {
        return System.getProperty("abiquo.rabbitmq.virtualHost", "/");
    }

    public static Integer getConnectionTimeout()
    {
        return Integer.parseInt(System.getProperty("abiquo.rabbitmq.connectionTimeout", "0"));
    }

    public static Integer getRequestedHeartbeat()
    {
        return Integer.parseInt(System.getProperty("abiquo.rabbitmq.requestedHeartbeat", "0"));
    }

    protected DefaultConfiguration()
    {
        LOGGER.trace(String.format("RabbitMQ configuration. Host: %s, port: %d, username: %s",
            getHost(), getPort(), getUserName()));
    }
}
