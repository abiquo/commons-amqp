/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.bpm.domain;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterNotification;
import com.abiquo.commons.amqp.util.JSONUtils;

public class BPMResponse extends DatacenterNotification
{
    public enum BPMResponseType
    {
        TASK, JOB
    };

    public enum BPMExtraDataKeys
    {
        DISK_SIZE_BYTES
    };

    protected String id;

    protected BPMResponseType type;

    protected Map<BPMExtraDataKeys, Object> extraData;

    public String getId()
    {
        return id;
    }

    public void setId(final String id)
    {
        this.id = id;
    }

    public BPMResponseType getType()
    {
        return type;
    }

    public void setType(final BPMResponseType type)
    {
        this.type = type;
    }

    public Map<BPMExtraDataKeys, Object> getExtraData()
    {
        return extraData;
    }

    public void setExtraData(final Map<BPMExtraDataKeys, Object> extraData)
    {
        this.extraData = extraData;
    }

    public static BPMResponse fromByteArray(final byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, BPMResponse.class);
    }

    @Override
    @JsonIgnore
    public boolean isTaskNotification()
    {
        return getType().equals(BPMResponseType.TASK);
    }

    @Override
    @JsonIgnore
    public boolean isJobNotification()
    {
        return getType().equals(BPMResponseType.JOB);
    }

    @Override
    @JsonIgnore
    public String getNotificationIdentifier()
    {
        return getId();
    }
}
