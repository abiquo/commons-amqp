/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.operations;

import com.abiquo.commons.amqp.impl.tarantino.domain.DiskSnapshot;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.PrimaryDisk;

/**
 * Moves the {@link VirtualMachineDefinition} {@link PrimaryDisk} to the datacenter repository.
 */
public class SnapshotVirtualMachineOp extends DatacenterJob
{
    /** Target Disk (in the repository) */
    protected DiskSnapshot diskSnapshot;

    public DiskSnapshot getDiskSnapshot()
    {
        return diskSnapshot;
    }

    public void setDiskSnapshot(DiskSnapshot diskSnapshot)
    {
        this.diskSnapshot = diskSnapshot;
    }
}
