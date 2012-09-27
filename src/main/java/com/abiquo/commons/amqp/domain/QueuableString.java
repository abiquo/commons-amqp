/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.domain;

import com.abiquo.rsmodel.amqp.Queuable;

public class QueuableString implements Queuable
{
    protected String message;

    public QueuableString(final String value)
    {
        this.message = value;
    }

    @Override
    public byte[] toByteArray()
    {
        return message.getBytes();
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(final String message)
    {
        this.message = message;
    }
}
