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

package com.abiquo.commons.amqp.impl.datacenter.domain.builder;

import com.abiquo.commons.amqp.impl.datacenter.domain.HypervisorConnection.HypervisorType;
import com.abiquo.commons.amqp.impl.datacenter.domain.VirtualMachineDefinition;
import com.abiquo.commons.amqp.impl.datacenter.domain.operations.ReconfigureVirtualMachineOp;

public class ReconfigureVirtualMachineJobBuilder extends ConfigureVirtualMachineJobBuilder
{
    VirtualMachineDefinition newVmachineDefinition;

    public ReconfigureVirtualMachineJobBuilder connection(HypervisorType hypervisortype, String ip,
        String loginUser, String loginPassword)
    {
        super.connection(hypervisortype, ip, loginUser, loginPassword);

        return this;
    }

    public ReconfigureVirtualMachineJobBuilder setVirtualMachineDefinition(
        VirtualMachineDescriptionBuilder vmBuilder, String virtualMachineId)
    {
        super.setVirtualMachineDefinition(vmBuilder, virtualMachineId);

        return this;
    }

    public ReconfigureVirtualMachineJobBuilder setNewVirtualMachineDefinition(
        VirtualMachineDescriptionBuilder newVmBuilder, String virtualMachineId)
    {
        newVmachineDefinition = newVmBuilder.build(virtualMachineId);

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
