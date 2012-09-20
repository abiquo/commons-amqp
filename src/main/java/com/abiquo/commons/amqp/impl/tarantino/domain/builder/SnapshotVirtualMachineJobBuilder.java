/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.builder;

import com.abiquo.commons.amqp.impl.tarantino.domain.DiskSnapshot;
import com.abiquo.commons.amqp.impl.tarantino.domain.DiskStandard;
import com.abiquo.commons.amqp.impl.tarantino.domain.DiskDescription.DiskFormatType;
import com.abiquo.commons.amqp.impl.tarantino.domain.HypervisorConnection.HypervisorType;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.SnapshotVirtualMachineOp;

public class SnapshotVirtualMachineJobBuilder extends VirtualFactoryJobBuilder
{
    private DiskSnapshot destination;

    public SnapshotVirtualMachineJobBuilder connection(HypervisorType hypervisortype, String ip,
        String loginUser, String loginPassword)
    {
        super.connection(hypervisortype, ip, loginUser, loginPassword);
        return this;
    }

    public SnapshotVirtualMachineJobBuilder setVirtualMachineDefinition(
        VirtualMachineDescriptionBuilder vmBuilder)
    {    
        super.setVirtualMachineDefinition(vmBuilder);

        return this;
    }

    public SnapshotVirtualMachineJobBuilder destinationDisk(DiskFormatType format,
        long capacityInBytes, String repository, String path, String snapshot)
    {
        destination = new DiskSnapshot();

        destination.setFormat(format);
        destination.setCapacityInBytes(capacityInBytes);
        destination.setRepository(repository);
        destination.setPath(path);
        destination.setSnapshotFilename(snapshot);

        return this;
    }

    public SnapshotVirtualMachineOp buildSnapshotVirtualMachineDto()
    {
        SnapshotVirtualMachineOp sn = new SnapshotVirtualMachineOp();
        sn.setHypervisorConnection(connection);
        sn.setVirtualMachine(vmachineDefinition);
        sn.setDiskSnapshot(destination);

        return sn;
    }
}
