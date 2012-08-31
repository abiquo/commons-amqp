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

package com.abiquo.commons.amqp.impl.am.domain;

import com.abiquo.commons.amqp.domain.Queuable;
import com.abiquo.commons.amqp.util.JSONUtils;

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
}
