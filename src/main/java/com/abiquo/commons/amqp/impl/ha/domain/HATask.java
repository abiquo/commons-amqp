/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.ha.domain;

import com.abiquo.commons.amqp.domain.Queuable;
import com.abiquo.commons.amqp.util.JSONUtils;

public class HATask implements Queuable
{
    protected int datacenterId;

    protected int machineId;

    protected int rackId;

    public int getDatacenterId()
    {
        return datacenterId;
    }

    public void setDatacenterId(int datacenterId)
    {
        this.datacenterId = datacenterId;
    }

    public int getMachineId()
    {
        return machineId;
    }

    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    public int getRackId()
    {
        return rackId;
    }

    public void setRackId(int rackId)
    {
        this.rackId = rackId;
    }

    @Override
    public byte[] toByteArray()
    {
        return JSONUtils.serialize(this);
    }

    public static HATask fromByteArray(final byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, HATask.class);
    }
}
