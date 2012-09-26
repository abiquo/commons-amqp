/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
/**
 * 
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;


/**
 * @author jdevesa
 */
public class SecondaryDiskStandard extends DiskStandard
{
    protected int sequence;

    /**
     * Identifier of the DiskManagement association (doesn't change)
     * */
    protected int diskManagementId;

    public int getSequence()
    {
        return sequence;
    }

    public void setSequence(final int value)
    {
        this.sequence = value;
    }

    public int getDiskManagementId()
    {
        return diskManagementId;
    }

    public void setDiskManagementId(int diskManagementId)
    {
        this.diskManagementId = diskManagementId;
    }
    
}
