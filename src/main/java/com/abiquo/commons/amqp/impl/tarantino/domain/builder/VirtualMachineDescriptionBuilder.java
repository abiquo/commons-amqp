/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.builder;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.abiquo.commons.amqp.impl.tarantino.domain.DhcpOptionCom;
import com.abiquo.commons.amqp.impl.tarantino.domain.DiskDescription.DiskControllerType;
import com.abiquo.commons.amqp.impl.tarantino.domain.DiskDescription.DiskFormatType;
import com.abiquo.commons.amqp.impl.tarantino.domain.DiskStandard;
import com.abiquo.commons.amqp.impl.tarantino.domain.DiskStateful;
import com.abiquo.commons.amqp.impl.tarantino.domain.SecondaryDiskStandard;
import com.abiquo.commons.amqp.impl.tarantino.domain.SecondaryDiskStateful;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.BootstrapConfiguration;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.Cdrom;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.EthernetDriver;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.HardwareConfiguration;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.NetworkConfiguration;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.PrimaryDisk;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.SecondaryDisks;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualNIC;
import com.abiquo.commons.amqp.impl.tarantino.domain.exception.BuilderException;
import com.abiquo.commons.amqp.impl.tarantino.domain.exception.BuilderException.VirtualMachineDescriptionBuilderError;

public class VirtualMachineDescriptionBuilder
{
    private String uuid;

    private String name;

    private HardwareConfiguration hardConf;

    private NetworkConfiguration netConf;

    private PrimaryDisk primaryDisk;

    private SecondaryDisks secondaryDisks;

    private BootstrapConfiguration bootstrapConf;

    private boolean isHA;

    private boolean hasDvd = false;

    private boolean isImported;

    public VirtualMachineDescriptionBuilder hardware(final int virtualCpu, final int ramInMb)
    {
        hardConf = new HardwareConfiguration();
        hardConf.setNumVirtualCpus(virtualCpu);
        hardConf.setRamInMb(ramInMb);

        return this;
    }

    public VirtualMachineDescriptionBuilder setRdPort(final int rdport)
    {
        if (netConf == null)
        {
            netConf = new NetworkConfiguration();
        }
        netConf.setRdPort(rdport);

        return this;
    }

    public VirtualMachineDescriptionBuilder setBasics(final String uuid, final String name)
    {
        this.uuid = uuid;
        this.name = name;

        return this;
    }

    public VirtualMachineDescriptionBuilder setHA(final boolean isHA)
    {
        this.isHA = isHA;

        return this;
    }

    public VirtualMachineDescriptionBuilder addNetwork(final String macAddress, final String ip,
        final String vSwitchName, final String networkName, final int vlanTag,
        final String leaseName, final String forwardMode, final String netAddress,
        final String gateway, final String mask, final String primaryDNS,
        final String secondaryDNS, final String sufixDNS, final int sequence,
        final List<DhcpOptionCom> list, final boolean configureGateway, final boolean isUnmanaged,
        final EthernetDriver driver)
    {
        if (netConf == null)
        {
            netConf = new NetworkConfiguration();
        }

        final VirtualNIC nic = new VirtualNIC();
        nic.setMacAddress(macAddress);
        nic.setIp(ip);

        nic.setVSwitchName(vSwitchName);
        nic.setNetworkName(networkName);
        nic.setVlanTag(vlanTag);
        nic.setSequence(sequence);

        nic.setLeaseName(leaseName);
        nic.setForwardMode(forwardMode);
        nic.setNetAddress(netAddress);
        nic.setGateway(gateway);
        nic.setMask(mask);
        nic.setPrimaryDNS(primaryDNS);
        nic.setSecondaryDNS(secondaryDNS);
        nic.setSufixDNS(sufixDNS);
        nic.setDhcpOptions(list);
        nic.setConfigureGateway(configureGateway);
        nic.setUnmanaged(isUnmanaged);

        if (driver != null)
        {
            nic.setEthernetDriver(driver);
        }

        netConf.getVirtualNICList().add(nic);

        return this;
    }

    public VirtualMachineDescriptionBuilder addNetwork(final String macAddress, final String ip,
        final String vSwitchName, final String networkName, final int vlanTag,
        final String leaseName, final String forwardMode, final String netAddress,
        final String gateway, final String mask, final String primaryDNS,
        final String secondaryDNS, final String sufixDNS, final int sequence,
        final List<DhcpOptionCom> list, final boolean configureGateway, final boolean isUnmanaged)
    {
        return addNetwork(macAddress, ip, vSwitchName, networkName, vlanTag, leaseName,
            forwardMode, netAddress, gateway, mask, primaryDNS, secondaryDNS, sufixDNS, sequence,
            list, configureGateway, isUnmanaged, null);
    }

    public VirtualMachineDescriptionBuilder dhcp(final String dhcpAddress, final Integer dhcpPort)
    {
        if (netConf == null)
        {
            netConf = new NetworkConfiguration();
        }

        netConf.setDhcpAddress(dhcpAddress);
        netConf.setDhcpPort(dhcpPort);
        return this;
    }

    public VirtualMachineDescriptionBuilder setRdPassword(final String rdpassword)
    {
        if (netConf == null)
        {
            netConf = new NetworkConfiguration();
        }
        netConf.setRdPassword(rdpassword);

        return this;
    }

    public VirtualMachineDescriptionBuilder setKeyMap(final String keymap)
    {
        if (netConf == null)
        {
            netConf = new NetworkConfiguration();
        }
        netConf.setKeyMap(keymap);

        return this;
    }

    public VirtualMachineDescriptionBuilder bootstrap(final String uri, final String auth)
    {
        bootstrapConf = new BootstrapConfiguration();
        bootstrapConf.setUri(uri);
        bootstrapConf.setAuth(auth);

        return this;
    }

    public VirtualMachineDescriptionBuilder primaryDisk(final DiskFormatType format,
        final long capacityInBytes, final String repository, final String sourcePath,
        final String destinationDatastore, final String repositoryManagerAddress,
        final DiskControllerType controllerType)
    {

        final DiskStandard disk = new DiskStandard();
        disk.setFormat(format);
        disk.setCapacityInBytes(capacityInBytes);
        disk.setRepository(repository);
        disk.setPath(sourcePath);
        disk.setDestinationDatastore(destinationDatastore);
        disk.setRepositoryManagerAddress(repositoryManagerAddress);
        disk.setDiskControllerType(controllerType);

        primaryDisk = new PrimaryDisk();
        primaryDisk.setDiskStandard(disk);

        // If repository is not present; it is an imported one
        isImported = StringUtils.isBlank(disk.getRepository());

        return this;
    }

    public VirtualMachineDescriptionBuilder primaryDisk(final DiskFormatType format,
        final long capacityInBytes, final String iqn, final String destinationDatastore,
        final DiskControllerType controllerType, final String name)
    {
        final DiskStateful disk = new DiskStateful();
        disk.setFormat(format);
        disk.setCapacityInBytes(capacityInBytes);
        disk.setLocation(iqn);
        disk.setDestinationDatastore(destinationDatastore);
        disk.setDiskControllerType(controllerType);
        disk.setName(name); // Used in XenServer

        primaryDisk = new PrimaryDisk();
        primaryDisk.setDiskStateful(disk);

        // Never will be imported, because it is stateful
        isImported = false;

        return this;
    }

    public VirtualMachineDescriptionBuilder addSecondaryScsiDisk(final DiskFormatType format,
        final long capacityInBytes, final String iqn, final String destinationDatastore,
        final int sequence, final DiskControllerType controllerType, final String name)
    {
        if (secondaryDisks == null)
        {
            secondaryDisks = new SecondaryDisks();
        }

        final SecondaryDiskStateful auxDisk = new SecondaryDiskStateful();
        auxDisk.setFormat(format);
        auxDisk.setCapacityInBytes(capacityInBytes);
        auxDisk.setLocation(iqn);
        auxDisk.setDestinationDatastore(destinationDatastore);
        auxDisk.setSequence(sequence);
        auxDisk.setDiskControllerType(controllerType);
        auxDisk.setName(name); // Used in XenServer

        secondaryDisks.getStatefulDisks().add(auxDisk);

        return this;
    }

    public VirtualMachineDescriptionBuilder addSecondaryHardDisk(final long diskFileSizeInBytes,
        final int sequence, final String datastorePath, final DiskControllerType controllerType,
        final Integer diskManagementId)
    {
        if (secondaryDisks == null)
        {
            secondaryDisks = new SecondaryDisks();
        }

        final SecondaryDiskStandard hdDisk = new SecondaryDiskStandard();
        hdDisk.setDiskManagementId(diskManagementId);
        hdDisk.setCapacityInBytes(0l);
        hdDisk.setDestinationDatastore(datastorePath);
        hdDisk.setDiskFileSizeInBytes(diskFileSizeInBytes);
        hdDisk.setFormat(null);
        hdDisk.setPath(null);
        hdDisk.setRepository(null);
        hdDisk.setRepositoryManagerAddress(null);
        hdDisk.setSequence(sequence);
        hdDisk.setDiskControllerType(controllerType);

        secondaryDisks.getStandardDisks().add(hdDisk);

        return this;
    }

    public VirtualMachineDescriptionBuilder setHasDvd()
    {
        this.hasDvd = true;

        return this;
    }

    public VirtualMachineDefinition build()
    {
        final VirtualMachineDefinition virtualMachine = new VirtualMachineDefinition();
        // TODO check not null
        virtualMachine.setMachineUUID(uuid);
        virtualMachine.setMachineName(name);
        virtualMachine.setHardwareConfiguration(hardConf);
        virtualMachine.setNetworkConfiguration(netConf);
        virtualMachine.setBootstrap(bootstrapConf);

        virtualMachine.setPrimaryDisk(primaryDisk);
        virtualMachine.setSecondaryDisks(secondaryDisks);
        virtualMachine.setHA(isHA);
        virtualMachine.setImported(isImported);

        if (hasDvd)
        {
            virtualMachine.setCdrom(new Cdrom());
        }

        if (primaryDisk == null)
        {
            throw new BuilderException(VirtualMachineDescriptionBuilderError.NO_PRIMARY_DISK);
        }
        if (uuid == null || name == null)
        {
            throw new BuilderException(VirtualMachineDescriptionBuilderError.NO_IDENTIFIED);
        }
        if (hardConf == null)
        {
            throw new BuilderException(VirtualMachineDescriptionBuilderError.NO_HARDWARE);
        }

        return DiskSequenceToBusAndUnitNumber.numerateBusAndUnitBasedOnSequences(virtualMachine);
    }

}// create builder
