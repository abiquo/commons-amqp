/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.consumer.retry;

import com.abiquo.commons.amqp.consumer.RetryStrategy;

public class NeverRetryStrategy extends RetryStrategy
{
    @Override
    public boolean shouldRetry()
    {
        return false;
    }
}
