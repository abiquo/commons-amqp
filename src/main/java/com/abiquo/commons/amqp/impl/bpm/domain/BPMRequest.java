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

import java.util.ArrayList;
import java.util.List;

import com.abiquo.commons.amqp.impl.bpm.domain.job.AbstractBPMJob;
import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterRequest;
import com.abiquo.commons.amqp.util.JSONUtils;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
public class BPMRequest extends DatacenterRequest
{
    public enum BPMRequestType
    {
        /** Disk conversion task */
        CONVERT_DISK,

        /** Persistent disk creation task */
        MAKE_PERSISTENT,

        /** Instance of a persistent disk task */
        INSTANCE_PERSISTENT;
    }

    private String taskId;

    private BPMRequestType type;

    private List<AbstractBPMJob> jobs;

    public BPMRequest()
    {
        this.jobs = new ArrayList<AbstractBPMJob>();
    }

    public void addJob(final AbstractBPMJob job)
    {
        this.jobs.add(job);
    }

    public String getTaskId()
    {
        return taskId;
    }

    // needed for serialization
    public void setTaskId(final String taskId)
    {
        this.taskId = taskId;
    }

    public BPMRequestType getType()
    {
        return type;
    }

    public void setType(final BPMRequestType type)
    {
        this.type = type;
    }

    public List<AbstractBPMJob> getJobs()
    {
        return jobs;
    }

    public void setJobs(final List<AbstractBPMJob> jobs)
    {
        this.jobs = jobs;
    }

    public static BPMRequest fromByteArray(final byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, BPMRequest.class);
    }
}
