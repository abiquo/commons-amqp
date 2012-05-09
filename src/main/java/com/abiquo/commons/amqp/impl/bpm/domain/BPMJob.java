package com.abiquo.commons.amqp.impl.bpm.domain;

import java.util.UUID;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
public class BPMJob
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

    public BPMJob()
    {
        this.setJobId(UUID.randomUUID().toString());
    }

    public String getJobId()
    {
        return jobId;
    }

    // needed for serialization
    private void setJobId(final String jobId)
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
