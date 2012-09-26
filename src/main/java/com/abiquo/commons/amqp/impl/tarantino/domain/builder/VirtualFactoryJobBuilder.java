/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.builder;

import com.abiquo.commons.amqp.impl.tarantino.domain.HypervisorConnection;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition;
import com.abiquo.commons.amqp.impl.tarantino.domain.HypervisorConnection.HypervisorType;

public class VirtualFactoryJobBuilder
{

    protected HypervisorConnection connection;

    protected VirtualMachineDefinition vmachineDefinition;

    public VirtualFactoryJobBuilder setVirtualMachineDefinition(
        VirtualMachineDescriptionBuilder vmBuilder)
    {
        vmachineDefinition = vmBuilder.build();

        return this;
    }

    public VirtualFactoryJobBuilder connection(HypervisorType hypervisortype, String ip,
        String loginUser, String loginPassword)
    {
        connection = new HypervisorConnection();
        connection.setHypervisorType(hypervisortype);
        connection.setIp(ip);
        connection.setLoginUser(loginUser);
        connection.setLoginPassword(loginPassword);

        return this;
    }

}
