/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.manual;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.abiquo.commons.amqp.util.RabbitMQUtils;

public class PingManualTest
{
    @Test(enabled = false)
    public void ping()
    {
        AssertJUnit.assertTrue(RabbitMQUtils.pingRabbitMQ());
    }
}
