/**
 * Abiquo community edition
 * cloud management application for hybrid clouds
 * Copyright (C) 2008-2010 - Abiquo Holdings S.L.
 * 
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
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
