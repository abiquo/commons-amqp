/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.consumer.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.consumer.RetryStrategy;

/**
 * Retry strategy that adds a timeout between retry attempts.
 * 
 * @author eruiz
 */
public class DelayedRetryStrategy extends RetryStrategy
{
    private final static Logger LOGGER = LoggerFactory.getLogger(DelayedRetryStrategy.class);

    protected int retriesLeft;

    protected long msToSleep;

    protected boolean infiniteRetries;

    public DelayedRetryStrategy()
    {
        this.retriesLeft = getNumberOfRetries();
        this.msToSleep = getMsToSleep();
        this.infiniteRetries = (this.retriesLeft == 0);
    }

    /**
     * Number of retries to perform. 0 for infinite retries.
     * 
     * @return The number of retries to perform.
     */
    protected int getNumberOfRetries()
    {
        return Integer.parseInt(System.getProperty("abiquo.rabbitmq.retry.retries", "0"));
    }

    /**
     * Milliseconds to sleep between each retry.
     * 
     * @return The milliseconds to sleep between each retry.
     */
    protected long getMsToSleep()
    {
        return Integer.parseInt(System.getProperty("abiquo.rabbitmq.retry.mstosleep", "10000"));
    }

    @Override
    public boolean shouldRetry()
    {
        boolean retry = infiniteRetries ? true : (retriesLeft-- > 0);

        if (retry)
        {
            try
            {
                LOGGER.debug(String.format(
                    "Sleeping for %d ms, %d retries left, infinite retries %b", msToSleep,
                    retriesLeft, infiniteRetries));
                Thread.sleep(msToSleep);
            }
            catch (InterruptedException e)
            {
                LOGGER.debug("Interrupted on thread sleep");
            }
        }

        return retry;
    }
}
