/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino;

import static com.abiquo.commons.amqp.impl.tarantino.TestJobs.testApplyVirtualMachineState;
import static com.abiquo.commons.amqp.impl.tarantino.TestJobs.testReconfigureVirtualMachine;
import static com.abiquo.commons.amqp.impl.tarantino.TestJobs.testSnapshotVirtualMachine;
import static com.abiquo.commons.amqp.impl.tarantino.TestJobs.testVirtualMachine;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterRequest;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.Cdrom;
import com.abiquo.commons.amqp.impl.tarantino.domain.dto.DatacenterTasks;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.ApplyVirtualMachineStateOp;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.ReconfigureVirtualMachineOp;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.SnapshotVirtualMachineOp;

public class SerializationTest
{
    @Test
    public void test_ApplyVirtualMachineStateOpSerialization()
    {
        ApplyVirtualMachineStateOp operation = buildApplyVirtualMachineStateOp();
        operation.setId("some tasks.job");
        DatacenterTasks tlist = new DatacenterTasks();
        tlist.getJobs().add(operation);
        tlist.setDependent(false);
        tlist.setId("some tasks");

        String serialization = new String(tlist.toByteArray());
        DatacenterRequest deserialization = DatacenterTasks.fromByteArray(serialization.getBytes());
        assertNotNull(deserialization);
    }

    @Test
    public void test_ApplyVirtualMachineStateOpSerializationWithDvd()
    {
        ApplyVirtualMachineStateOp operation = buildApplyVirtualMachineStateOp();
        operation.getVirtualMachine().setCdrom(new Cdrom());
        operation.setId("some tasks.job");
        DatacenterTasks tlist = new DatacenterTasks();
        tlist.getJobs().add(operation);
        tlist.setDependent(false);
        tlist.setId("some tasks");

        String serialization = new String(tlist.toByteArray());
        DatacenterRequest deserialization = DatacenterTasks.fromByteArray(serialization.getBytes());
        assertNotNull(deserialization);
    }

    @Test
    public void test_ReconfigureVirtualMachineOpSerialization()
    {
        ReconfigureVirtualMachineOp operation = buildReconfigureVirtualMachineOp();
        operation.setId("some tasks.job");
        DatacenterTasks tlist = new DatacenterTasks();
        tlist.getJobs().add(operation);
        tlist.setDependent(false);
        tlist.setId("some tasks");

        String serialization = new String(tlist.toByteArray());
        DatacenterRequest deserialization = DatacenterTasks.fromByteArray(serialization.getBytes());
        assertNotNull(deserialization);
    }

    @Test
    public void test_SnapshotVirtualMachineOpSerialization()
    {
        SnapshotVirtualMachineOp operation = buildSnapshotVirtualMachineOp();
        operation.setId("some tasks.job");
        DatacenterTasks tlist = new DatacenterTasks();
        tlist.getJobs().add(operation);
        tlist.setDependent(false);
        tlist.setId("some tasks");

        String serialization = new String(tlist.toByteArray());
        DatacenterRequest deserialization = DatacenterTasks.fromByteArray(serialization.getBytes());
        assertNotNull(deserialization);
    }

    private ApplyVirtualMachineStateOp buildApplyVirtualMachineStateOp()
    {
        return testApplyVirtualMachineState(testVirtualMachine());
    }

    private ReconfigureVirtualMachineOp buildReconfigureVirtualMachineOp()
    {
        return testReconfigureVirtualMachine(testVirtualMachine());
    }

    private SnapshotVirtualMachineOp buildSnapshotVirtualMachineOp()
    {
        return testSnapshotVirtualMachine(testVirtualMachine());
    }
}
