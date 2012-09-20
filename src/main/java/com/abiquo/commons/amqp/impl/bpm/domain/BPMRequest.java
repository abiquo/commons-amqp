/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.bpm.domain;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

import com.abiquo.commons.amqp.impl.bpm.domain.job.AbstractBPMJob;
import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterRequest;
import com.abiquo.commons.amqp.util.JSONUtils;

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
