/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.operations;

import com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition;

/**
 * Change the virtual machine state. @see {@link StateTransition}
 */
public class ApplyVirtualMachineStateOp extends DatacenterJob
{
    protected StateTransition transaction;

    public StateTransition getTransaction()
    {
        return transaction;
    }

    public void setTransaction(StateTransition transaction)
    {
        this.transaction = transaction;
    }
}
