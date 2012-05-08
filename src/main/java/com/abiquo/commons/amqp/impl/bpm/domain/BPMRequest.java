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
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterRequest;
import com.abiquo.commons.amqp.util.JSONUtils;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
public class BPMRequest extends DatacenterRequest
{
    public enum TYPE
    {
        PERSISTENT, CONVERSION;
    }

    private String taskID;

    private TYPE type;

    private List<BPMJob> jobs;

    public String getTaskID()
    {
        return taskID;
    }

    // needed for serialization
    private void setTaskID(final String taskID)
    {
        this.taskID = taskID;
    }

    public TYPE getType()
    {
        return type;
    }

    public void setType(final TYPE type)
    {
        this.type = type;
    }

    public List<BPMJob> getJobs()
    {
        return jobs;
    }

    public void setJobs(final List<BPMJob> jobs)
    {
        this.jobs = jobs;
    }

    public BPMRequest()
    {

    }

    public BPMRequest(final TYPE type)
    {
        this.setTaskID(UUID.randomUUID().toString());
        this.jobs = new ArrayList<BPMJob>();
        this.type = type;
    }

    public void addJob(final BPMJob job)
    {
        this.jobs.add(job);
    }

    public static BPMRequest fromByteArray(final byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, BPMRequest.class);
    }
}
