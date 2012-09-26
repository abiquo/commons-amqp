/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.bpm.domain.job;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
public abstract class AbstractBPMJob
{
    public enum BPMJobType
    {
        /** A disk conversion job (v2v script) */
        DISK_CONVERSION,

        /** A fill volume job, for persistent creation (mechadora script) */
        DUMP_DISK_TO_VOLUME,

        /** Disk dump from volume, for persistent instances (mechadora script) */
        DUMP_VOLUME_TO_DISK,
    }

    private String jobId;

    private BPMJobType type;

    public AbstractBPMJob(BPMJobType type)
    {
        this.type = type;
    }

    public String getJobId()
    {
        return jobId;
    }

    public void setJobId(final String jobId)
    {
        this.jobId = jobId;
    }

    public BPMJobType getType()
    {
        return type;
    }

    public void setType(final BPMJobType type)
    {
        this.type = type;
    }
}
