/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.vsm.domain;

import com.abiquo.commons.amqp.domain.Queuable;
import com.abiquo.commons.amqp.util.JSONUtils;

public class VirtualSystemEvent implements Queuable
{
    protected String virtualSystemId;

    protected String virtualSystemType;

    protected String virtualSystemAddress;

    protected String eventType;

    public VirtualSystemEvent()
    {

    }

    public VirtualSystemEvent(String virtualSystemId, String virtualSystemType,
        String virtualSystemAddress, String eventType)
    {
        this.virtualSystemId = virtualSystemId;
        this.virtualSystemType = virtualSystemType;
        this.virtualSystemAddress = virtualSystemAddress;
        this.eventType = eventType;
    }

    public String getVirtualSystemId()
    {
        return virtualSystemId;
    }

    public void setVirtualSystemId(String virtualSystemId)
    {
        this.virtualSystemId = virtualSystemId;
    }

    public String getVirtualSystemType()
    {
        return virtualSystemType;
    }

    public void setVirtualSystemType(String virtualSystemType)
    {
        this.virtualSystemType = virtualSystemType;
    }

    public String getVirtualSystemAddress()
    {
        return virtualSystemAddress;
    }

    public void setVirtualSystemAddress(String virtualSystemAddress)
    {
        this.virtualSystemAddress = virtualSystemAddress;
    }

    public String getEventType()
    {
        return eventType;
    }

    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    @Override
    public byte[] toByteArray()
    {
        return JSONUtils.serialize(this);
    }

    public static VirtualSystemEvent fromByteArray(byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, VirtualSystemEvent.class);
    }
}
