/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.am.domain;

import com.abiquo.commons.amqp.domain.Queuable;
import com.abiquo.commons.amqp.util.JSONUtils;

public class TemplateStatusEvent implements Queuable
{
    protected String ovfId;

    protected String status;

    protected Double progress;

    protected String enterpriseId;

    protected String repositoryLocation;

    /** only for ERROR events */
    protected String errorCause;

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

    public String getRepositoryLocation()
    {
        return repositoryLocation;
    }

    public void setRepositoryLocation(final String repositoryLocation)
    {
        this.repositoryLocation = repositoryLocation;
    }

    @Override
    public byte[] toByteArray()
    {
        return JSONUtils.serialize(this);
    }

    public static TemplateStatusEvent fromByteArray(final byte[] bytes)
    {
        return JSONUtils.deserialize(bytes, TemplateStatusEvent.class);
    }
}
