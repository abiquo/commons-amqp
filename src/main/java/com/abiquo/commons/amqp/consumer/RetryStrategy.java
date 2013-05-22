/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.consumer;

/**
 * {@link AMQPConsumer} uses this to automate the retry strategy when RabbitMQ goes down.
 * 
 * @author eruiz
 */
public abstract class RetryStrategy
{
    /**
     * Should retry to connect
     * 
     * @return True if should retry. Otherwise false.
     */
    public abstract boolean shouldRetry();
}
