/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter.domain;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

import com.abiquo.commons.amqp.domain.Queuable;
import com.abiquo.commons.amqp.util.JSONUtils;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
public abstract class DatacenterNotification implements Queuable
{
    @Override
    public byte[] toByteArray()
    {
        return JSONUtils.serialize(this);
    }

    public static DatacenterNotification fromByteArray(final byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, DatacenterNotification.class);
    }
}
