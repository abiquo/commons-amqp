/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.builder;

import com.abiquo.commons.amqp.impl.tarantino.domain.HypervisorConnection.HypervisorType;
import com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.ApplyVirtualMachineStateOp;

public class ApplyVirtualMachineStateJobBuilder extends VirtualFactoryJobBuilder
{
    private StateTransition transaction;

    @Override
    public ApplyVirtualMachineStateJobBuilder connection(final HypervisorType hypervisortype,
        final String ip, final String loginUser, final String loginPassword)
    {
        super.connection(hypervisortype, ip, loginUser, loginPassword);
        return this;
    }

    @Override
    public ApplyVirtualMachineStateJobBuilder setVirtualMachineDefinition(
        final VirtualMachineDescriptionBuilder vmBuilder)
    {
        super.setVirtualMachineDefinition(vmBuilder);

        return this;
    }

    public ApplyVirtualMachineStateJobBuilder state(final StateTransition transition)
    {
        this.transaction = transition;

        return this;
    }

    public ApplyVirtualMachineStateOp buildApplyVirtualMachineStateDto()
    {
        ApplyVirtualMachineStateOp vmaction = new ApplyVirtualMachineStateOp();
        vmaction.setHypervisorConnection(connection);
        vmaction.setVirtualMachine(vmachineDefinition);
        vmaction.setTransaction(transaction);

        return vmaction;
    }
}
