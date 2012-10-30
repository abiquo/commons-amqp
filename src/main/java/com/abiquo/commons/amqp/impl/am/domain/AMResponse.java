/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.am.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.abiquo.rsmodel.amqp.Queuable;
import com.abiquo.rsmodel.amqp.util.JSONUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AMResponse implements Queuable
{
    /** identify the event **/

    private String taskId;

    protected String datacenterUuid;

    protected String enterpriseId;

    protected String ovfId;

    /** kind of event **/
    // TODO use TemplateStatusEnumType
    protected String status;

    /** Additional data based of kind of event **/
    /** only during ERROR */
    protected String errorCause;

    /** only during DOWNLOADING */
    protected Double progress;


    public String getOvfId()
    {
        return ovfId;
    }

    public void setOvfId(final String ovfId)
    {
        this.ovfId = ovfId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(final String status)
    {
        this.status = status;
    }

    public Double getProgress()
    {
        return progress;
    }

    public void setProgress(final Double progress)
    {
        this.progress = progress;
    }

    public String getErrorCause()
    {
        return errorCause;
    }

    public void setErrorCause(final String errorCause)
    {
        this.errorCause = errorCause;
    }

    public String getEnterpriseId()
    {
        return enterpriseId;
    }

    public void setEnterpriseId(final String enterpriseId)
    {
        this.enterpriseId = enterpriseId;
    }

    public String getDatacenterUuid()
    {
        return datacenterUuid;
    }

    public void setDatacenterUuid(final String datacenterUuid)
    {
        this.datacenterUuid = datacenterUuid;
    }

    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(final String taskId)
    {
        this.taskId = taskId;
    }

    public static AMResponse fromByteArray(final byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, AMResponse.class);
    }

    @Override
    public byte[] toByteArray()
    {
        return JSONUtils.serialize(this);
    }

    /** This kind of creation haven't an associated task */
    @JsonIgnore
    private final static String TASK_UPLOAD = "upload";

    @JsonIgnore
    private final static String TASK_DELETE = "delete";

    @JsonIgnore
    private final static Set<String> NOT_TASK_ASSOCIATED = new HashSet<String>(Arrays
        .asList(new String[] {TASK_UPLOAD, TASK_DELETE}));

    @JsonIgnore
    public boolean isAssociatedTask()
    {
        return !NOT_TASK_ASSOCIATED.contains(getTaskId());
    }

    @JsonIgnore
    public boolean isUpload()
    {
        return TASK_UPLOAD.equalsIgnoreCase(getTaskId());
    }

    @JsonIgnore
    public boolean isDelete()
    {
        return TASK_DELETE.equalsIgnoreCase(getTaskId());
    }
}
