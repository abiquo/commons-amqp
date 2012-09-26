/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.operations;

import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition;

/**
 * Updates a {@link VirtualMachineDefinition}
 */
public class ReconfigureVirtualMachineOp extends DatacenterJob
{
    protected VirtualMachineDefinition newVirtualMachine;

    public VirtualMachineDefinition getNewVirtualMachine()
    {
        return newVirtualMachine;
    }

    public void setNewVirtualMachine(VirtualMachineDefinition newVirtualMachine)
    {
        this.newVirtualMachine = newVirtualMachine;
    }
}
