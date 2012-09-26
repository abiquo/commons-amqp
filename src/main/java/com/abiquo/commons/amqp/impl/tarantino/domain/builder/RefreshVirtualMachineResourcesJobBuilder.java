/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.builder;

import com.abiquo.commons.amqp.impl.tarantino.domain.HypervisorConnection.HypervisorType;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.RefreshVirtualMachineResourcesOp;

public class RefreshVirtualMachineResourcesJobBuilder extends VirtualFactoryJobBuilder
{
    @Override
    public RefreshVirtualMachineResourcesJobBuilder connection(final HypervisorType hypervisortype,
        final String ip, final String loginUser, final String loginPassword)
    {
        super.connection(hypervisortype, ip, loginUser, loginPassword);
        return this;
    }

    @Override
    public RefreshVirtualMachineResourcesJobBuilder setVirtualMachineDefinition(
        final VirtualMachineDescriptionBuilder vmBuilder)
    {
        super.setVirtualMachineDefinition(vmBuilder);
        return this;
    }

    public RefreshVirtualMachineResourcesOp buildRefreshVirtualMachineResourcesDto()
    {
        RefreshVirtualMachineResourcesOp vmaction = new RefreshVirtualMachineResourcesOp();
        vmaction.setHypervisorConnection(connection);
        vmaction.setVirtualMachine(vmachineDefinition);
        return vmaction;
    }
}
