/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import com.rabbitmq.client.LongString;

public class LongStringUtils
{
    private LongStringUtils()
    {
    }

    public static boolean isLongStringAssignableFrom(final Object object)
    {
        return LongString.class.isAssignableFrom(object.getClass());
    }

    public static String makeString(final LongString longString)
    {
        return new String(longString.getBytes());
    }

}
