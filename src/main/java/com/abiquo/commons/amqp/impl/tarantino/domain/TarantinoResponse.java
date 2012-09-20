/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterNotification;
import com.abiquo.commons.amqp.impl.tarantino.domain.dto.BaseJob;
import com.abiquo.commons.amqp.util.JSONUtils;

public class TarantinoResponse extends DatacenterNotification
{
    public enum JobStateType
    {
        START, DONE, ERROR, ABORTED, ROLLBACK_START, ROLLBACK_DONE, ROLLBACK_ERROR, ROLLBACK_ABORTED
    };

    protected String jobId;

    protected JobStateType state;

    /** for ERROR and ROLLBACK_ERROR adds the cause. TODO use VirtualFactoryException. */
    protected String error;

    protected TarantinoError tarantinoError;

    public TarantinoError getTarantinoError()
    {
        return tarantinoError;
    }

    public void setTarantinoError(final TarantinoError tarantinoError)
    {
        this.tarantinoError = tarantinoError;
    }

    public String getJobId()
    {
        return jobId;
    }

    public void setJobId(final String jobId)
    {
        this.jobId = jobId;
    }

    public JobStateType getState()
    {
        return state;
    }

    public void setState(final JobStateType state)
    {
        this.state = state;
    }

    public String getError()
    {
        return error;
    }

    public void setError(final String error)
    {
        this.error = error;
    }

    @JsonIgnore
    public boolean isTask()
    {
        return BaseJob.isRoot(this.jobId);
    }

    public static TarantinoResponse fromByteArray(final byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, TarantinoResponse.class);
    }
}
