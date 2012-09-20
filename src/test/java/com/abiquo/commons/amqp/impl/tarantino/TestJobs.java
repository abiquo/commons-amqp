/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino;

import java.util.ArrayList;
import java.util.List;

import com.abiquo.commons.amqp.impl.tarantino.domain.DhcpOptionCom;
import com.abiquo.commons.amqp.impl.tarantino.domain.DiskDescription.DiskFormatType;
import com.abiquo.commons.amqp.impl.tarantino.domain.HypervisorConnection.HypervisorType;
import com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.HardwareConfiguration;
import com.abiquo.commons.amqp.impl.tarantino.domain.builder.ApplyVirtualMachineStateJobBuilder;
import com.abiquo.commons.amqp.impl.tarantino.domain.builder.ReconfigureVirtualMachineJobBuilder;
import com.abiquo.commons.amqp.impl.tarantino.domain.builder.RefreshVirtualMachineResourcesJobBuilder;
import com.abiquo.commons.amqp.impl.tarantino.domain.builder.SnapshotVirtualMachineJobBuilder;
import com.abiquo.commons.amqp.impl.tarantino.domain.builder.VirtualMachineDescriptionBuilder;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.ApplyVirtualMachineStateOp;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.ReconfigureVirtualMachineOp;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.RefreshVirtualMachineResourcesOp;
import com.abiquo.commons.amqp.impl.tarantino.domain.operations.SnapshotVirtualMachineOp;

public class TestJobs
{
    public static VirtualMachineDescriptionBuilder testVirtualMachine()
    {
        return new VirtualMachineDescriptionBuilder()
            //
            .setBasics("virtualMachineID", "ABQ_virtualMachineID").setHA(false)
            .hardware(1, 256)
            //
            .addNetwork("mac:mac:mac", "127.0.0.1", "vSwitchName", "networkName", 1, "leaseName",
                "forwardMode", "netAddress", "gateway", "mask", "primaryDNS", "secondaryDNS",
                "sufixDNS", 0, dhcp(), true, false, null) //
            // .primaryDisk("RAW", "1024", "iqn.bla.bla-lun-0")
            .primaryDisk(DiskFormatType.RAW, 1024l, "nfs-devel:/opt/vm_repo",
                "1/rs.bcn/m0n0/m0n0.iso", "datastore1", "http://localhost/am", null) //
            .addSecondaryScsiDisk(DiskFormatType.RAW, 1024l, "iqn....", "sdasd", 1, null,
                "scsi-disk");
    }

    private static List<DhcpOptionCom> dhcp()
    {
        List<DhcpOptionCom> dhcpList = new ArrayList<DhcpOptionCom>();

        DhcpOptionCom dhcp = new DhcpOptionCom();
        dhcp.setMask(0);
        dhcp.setOption(121);
        dhcp.setGateway("192.168.6.1");
        dhcp.setNetworkAddress("192.168.6.0");
        dhcp.setNetmask("255.255.255.0");

        dhcpList.add(dhcp);
        return dhcpList;
    }

    public static ApplyVirtualMachineStateOp testConfigureVirtualMachine(
        final VirtualMachineDescriptionBuilder vmbuilder)
    {
        return new ApplyVirtualMachineStateJobBuilder() //
            .connection(HypervisorType.TEST, "localhost", "root", "root") //
            .setVirtualMachineDefinition(vmbuilder) //
            .state(StateTransition.CONFIGURE) //
            .buildApplyVirtualMachineStateDto();
    }

    public static ApplyVirtualMachineStateOp testApplyVirtualMachineState(
        final VirtualMachineDescriptionBuilder vmbuilder)
    {
        return new ApplyVirtualMachineStateJobBuilder() //
            .connection(HypervisorType.TEST, "10.60.1.15", "root", "root") //
            .setVirtualMachineDefinition(vmbuilder) //
            .state(StateTransition.PAUSE)//
            .buildApplyVirtualMachineStateDto();
    }

    public static ApplyVirtualMachineStateOp testApplyVirtualMachineState(
        final VirtualMachineDescriptionBuilder vmbuilder, final StateTransition state)
    {
        return new ApplyVirtualMachineStateJobBuilder() //
            .connection(HypervisorType.TEST, "10.60.1.15", "root", "root") //
            .setVirtualMachineDefinition(vmbuilder) //
            .state(state)//
            .buildApplyVirtualMachineStateDto();
    }

    public static SnapshotVirtualMachineOp testSnapshotVirtualMachine(
        final VirtualMachineDescriptionBuilder vmbuilder)
    {
        return new SnapshotVirtualMachineJobBuilder()
            .connection(HypervisorType.TEST, "10.60.1.15", "root", "root") //
            .setVirtualMachineDefinition(vmbuilder) //
            .destinationDisk(DiskFormatType.RAW, 1024l, "nfs-devel:/opt/vm_repository",
                "1/some/bundle/m0n0.iso", "test-snapshot")//
            .buildSnapshotVirtualMachineDto();
    }

    public static ReconfigureVirtualMachineOp testReconfigureVirtualMachine(
        final VirtualMachineDescriptionBuilder vmbuilder)
    {
        HardwareConfiguration hwConf = new HardwareConfiguration();
        hwConf.setNumVirtualCpus(4);
        hwConf.setRamInMb(1000);

        VirtualMachineDefinition newVmDef = vmbuilder.build();
        newVmDef.setHardwareConfiguration(hwConf);

        return new ReconfigureVirtualMachineJobBuilder()//
            .connection(HypervisorType.TEST, "10.60.1.15", "root", "root") //
            .setVirtualMachineDefinition(vmbuilder) //
            .setNewVirtualMachineDefinition(newVmDef) //
            .buildReconfigureVirtualMachineDto();
    }

    /** A ram = 0 in the DummyHypervisor will raise an error during ROLLBACK (try ROLLBACK_ERROR) */
    public static ReconfigureVirtualMachineOp testReconfigureInvalidVirtualMachine(
        final VirtualMachineDescriptionBuilder vmbuilder)
    {
        HardwareConfiguration hwConf = new HardwareConfiguration();
        hwConf.setNumVirtualCpus(0);
        hwConf.setRamInMb(0);

        VirtualMachineDefinition newVmDef = vmbuilder.build();
        newVmDef.setHardwareConfiguration(hwConf);

        return new ReconfigureVirtualMachineJobBuilder()//
            .connection(HypervisorType.TEST, "10.60.1.15", "root", "root") //
            .setVirtualMachineDefinition(vmbuilder) //
            .setNewVirtualMachineDefinition(newVmDef) //
            .buildReconfigureVirtualMachineDto();
    }

    public static RefreshVirtualMachineResourcesOp testRefreshVirtualMachineResources(
        final VirtualMachineDescriptionBuilder vmbuilder)
    {
        return new RefreshVirtualMachineResourcesJobBuilder()
            .connection(HypervisorType.TEST, "10.60.1.15", "root", "root") //
            .setVirtualMachineDefinition(vmbuilder) //
            .buildRefreshVirtualMachineResourcesDto();
    }
}
