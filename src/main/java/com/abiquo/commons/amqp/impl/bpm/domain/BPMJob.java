package com.abiquo.commons.amqp.impl.bpm.domain;

import java.util.UUID;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
public class BPMJob
{
    public enum TYPE
    {
        CONVERSION, // any conversion type [V2V]
        DUMP_TO_VOLUME, // perisistent [MECHA]
        DUMP_TO_DISK, // instance of persistent (1 of 2) [MECHA]
        FROM_RAW_TO_ORIGIN, // instance of persistent (2 of 2) [V2V]
        INSTANCE; // from conversion instance to origin format [V2V]
    }

    private String jobID;

    private TYPE type;

    public String getJobID()
    {
        return jobID;
    }

    // needed for serialization
    private void setJobID(final String jobID)
    {
        this.jobID = jobID;
    }

    public TYPE getType()
    {
        return type;
    }

    public void setType(final TYPE type)
    {
        this.type = type;
    }

    public BPMJob()
    {
        this.setJobID(UUID.randomUUID().toString());
    }
}
