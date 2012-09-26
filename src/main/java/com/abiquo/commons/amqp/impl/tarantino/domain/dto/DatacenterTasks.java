/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.dto;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

import com.abiquo.commons.amqp.impl.tarantino.domain.operations.DatacenterJob;
import com.abiquo.commons.amqp.util.JSONUtils;

/**
 * Dependent or independent BaseJob collection
 */
@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
public class DatacenterTasks extends BaseJob
{
    // XXX consider using a map <jobid, BaseJob>
    private List<BaseJob> jobs;

    private Boolean dependent;

    public DatacenterTasks()
    {
        this.setId(UUID.randomUUID().toString());
    }

    public boolean isDependent()
    {
        return dependent;
    }

    public void setDependent(boolean dependent)
    {
        this.dependent = dependent;
    }

    public List<BaseJob> getJobs()
    {
        if (jobs == null)
        {
            jobs = new LinkedList<BaseJob>();
        }

        return jobs;
    }

    public List<String> getJobsId()
    {
        List<String> ids = new LinkedList<String>();
        for (BaseJob bj : jobs)
        {
            ids.add(bj.id);
        }
        return ids;
    }

    /**
     * Utility to get a job (child or recursive)
     * <p>
     * TODO use a BaseJob map
     * 
     * @return null if not found
     */
    public BaseJob getJob(final String jobId)
    {
        int dif = level(jobId) - level(id);

        if (dif <= 0)
        {
            return null; // invalid
        }

        if (dif == 1)
        {
            return getJobInCurrentLevel(jobId);
        }
        else
        {
            final String thisparent = getParentAt(jobId, level(id) + 1);
            return ((DatacenterTasks) getJobInCurrentLevel(thisparent)).getJob(jobId);
        }
    }

    private BaseJob getJobInCurrentLevel(final String jobId)
    {
        for (BaseJob bj : getJobs())
        {
            if (bj.getId().equalsIgnoreCase(jobId))
            {
                return bj;
            }
        }

        return null;
    }

    public static DatacenterTasks fromByteArray(final byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, DatacenterTasks.class);
    }

    /**
     * Only supports DatacenterJob
     * 
     * @param job
     */
    public void addDatacenterJob(DatacenterJob job)
    {
        job.setId(getId().concat(".").concat(UUID.randomUUID().toString()));
        getJobs().add(job);
    }
}
