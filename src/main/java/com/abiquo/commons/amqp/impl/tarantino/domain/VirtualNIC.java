/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.EthernetDriver;

public class VirtualNIC extends DHCPRule
{
    protected String vSwitchName;

    protected String networkName;

    protected int vlanTag;

    protected String forwardMode;

    protected Boolean unmanaged;

    protected List<DhcpOptionCom> dhcpOptions;

    protected EthernetDriver ethernetDriver;

    @Deprecated
    // use DCHPRule.ip
    protected String netAddress;

    protected int sequence;

    public String getVSwitchName()
    {
        return vSwitchName;
    }

    public void setVSwitchName(final String vSwitchName)
    {
        this.vSwitchName = vSwitchName;
    }

    @JsonIgnore
    public String getRuleName()
    {
        return "host_" + getMacAddress().replaceAll(":", "");
    }

    public String getNetworkName()
    {
        return networkName;
    }

    public void setNetworkName(final String value)
    {
        this.networkName = value;
    }

    public int getVlanTag()
    {
        return vlanTag;
    }

    public void setVlanTag(final int value)
    {
        this.vlanTag = value;
    }

    public String getForwardMode()
    {
        return forwardMode;
    }

    public void setForwardMode(final String value)
    {
        this.forwardMode = value;
    }

    @Deprecated
    // use DCHPRule.ip
    public String getNetAddress()
    {
        return netAddress;
    }

    @Deprecated
    // use DCHPRule.ip
    public void setNetAddress(final String value)
    {
        this.netAddress = value;
    }

    @Override
    public boolean equals(final Object obj)
    {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    public int getSequence()
    {
        return sequence;
    }

    public void setSequence(final int value)
    {
        this.sequence = value;
    }

    public List<DhcpOptionCom> getDhcpOptions()
    {
        return dhcpOptions;
    }

    public void setDhcpOptions(final List<DhcpOptionCom> dhcpOptions)
    {
        this.dhcpOptions = dhcpOptions;
    }

    public Boolean getUnmanaged()
    {
        return unmanaged;
    }

    public void setUnmanaged(final Boolean unmanaged)
    {
        this.unmanaged = unmanaged;
    }

    /**
     * Default driver is {@link EthernetDriver.E1000}
     */
    public EthernetDriver getEthernetDriver()
    {
        return ethernetDriver == null ? EthernetDriver.E1000 : ethernetDriver;
    }

    public void setEthernetDriver(final EthernetDriver networkDriver)
    {
        this.ethernetDriver = networkDriver;
    }
}
