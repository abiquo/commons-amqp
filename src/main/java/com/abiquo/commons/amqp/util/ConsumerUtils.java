/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;

/**
 * A collection of helper methods to be used by the producers.
 * 
 * @author eruiz@abiquo.com
 */
public class ConsumerUtils
{
    /**
     * {@link ExecutorService} used to start the reconnect logic asynchronously. We use the
     * {@link Executors#newSingleThreadExecutor()} implementation due the connection to rabbitmq has
     * failed in all the consumers, then we can wait for one consumer reconnection success and start
     * the remaining reconnection jobs.
     */
    public final static ExecutorService reconnectionExecutor = newSingleThreadExecutor();

    public static void startConsumerRequiredAck(final Channel channel, final Consumer consumer,
        final String queue) throws IOException
    {
        channel.basicConsume(queue, false, consumer);
    }

    public static void ackMessage(final Channel channel, final long tag) throws IOException
    {
        channel.basicAck(tag, false);
    }

    public static void rejectMessage(final Channel channel, final long tag) throws IOException
    {
        channel.basicReject(tag, false);
    }

    public static void rejectMessageAndRequeue(final Channel channel, final long tag)
        throws IOException
    {
        channel.basicReject(tag, true);
    }
}
