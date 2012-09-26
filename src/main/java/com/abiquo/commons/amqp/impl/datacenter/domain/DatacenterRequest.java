/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter.domain;

import com.abiquo.commons.amqp.domain.Queuable;
import com.abiquo.commons.amqp.util.JSONUtils;

public abstract class DatacenterRequest implements Queuable
{
    @Override
    public byte[] toByteArray()
    {
        return JSONUtils.serialize(this);
    }
}
