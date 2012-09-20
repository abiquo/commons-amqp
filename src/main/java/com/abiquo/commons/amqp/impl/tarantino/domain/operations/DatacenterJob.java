/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.operations;

import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

import com.abiquo.commons.amqp.impl.tarantino.domain.HypervisorConnection;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition;
import com.abiquo.commons.amqp.impl.tarantino.domain.dto.BaseJob;

/**
 * Carry common attributes to all the virtual machine operations.
 */
@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
public abstract class DatacenterJob extends BaseJob
{
    /** Hypervisor connection attributes. */
    protected HypervisorConnection hypervisorConnection;

    /** Operation target virtual machine */
    protected VirtualMachineDefinition virtualMachine;

    /** Retries performed by the supervisor (connection exception). */
    protected Integer retry = 0;

    public VirtualMachineDefinition getVirtualMachine()
    {
        return virtualMachine;
    }

    public void setVirtualMachine(VirtualMachineDefinition virtualMachine)
    {
        this.virtualMachine = virtualMachine;
    }

    public HypervisorConnection getHypervisorConnection()
    {
        return hypervisorConnection;
    }

    public void setHypervisorConnection(HypervisorConnection hypervisorConnection)
    {
        this.hypervisorConnection = hypervisorConnection;
    }

    public void incrementRetry()
    {
        retry++;
    }

    public Integer getRetry()
    {
        return retry;
    }

    public void setRetry(Integer retry)
    {
        this.retry = retry;
    }
}
