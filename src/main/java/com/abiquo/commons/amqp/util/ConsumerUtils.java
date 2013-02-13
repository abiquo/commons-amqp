/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
}
