/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.builder;

import com.abiquo.commons.amqp.impl.tarantino.domain.HypervisorConnection.HypervisorType;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.ReconfigureVirtualMachineOp;

public class ReconfigureVirtualMachineJobBuilder extends VirtualFactoryJobBuilder
{
    VirtualMachineDefinition newVmachineDefinition;

    @Override
    public ReconfigureVirtualMachineJobBuilder connection(final HypervisorType hypervisortype,
        final String ip, final String loginUser, final String loginPassword)
    {
        super.connection(hypervisortype, ip, loginUser, loginPassword);

        return this;
    }

    @Override
    public ReconfigureVirtualMachineJobBuilder setVirtualMachineDefinition(
        final VirtualMachineDescriptionBuilder vmBuilder)
    {
        super.setVirtualMachineDefinition(vmBuilder);

        return this;
    }

    public ReconfigureVirtualMachineJobBuilder setNewVirtualMachineDefinition(
        final VirtualMachineDefinition newVmBuilder)
    {
        newVmachineDefinition = newVmBuilder;

        return this;
    }

    public ReconfigureVirtualMachineOp buildReconfigureVirtualMachineDto()
    {
        ReconfigureVirtualMachineOp reconfigure = new ReconfigureVirtualMachineOp();
        reconfigure.setHypervisorConnection(this.connection);
        reconfigure.setVirtualMachine(this.vmachineDefinition);
        reconfigure.setNewVirtualMachine(this.newVmachineDefinition);

        return reconfigure;
    }
}
