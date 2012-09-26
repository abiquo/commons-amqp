/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

public class DHCPRule
{
    protected String ip;

    protected String macAddress;

    protected String leaseName;

    protected String gateway;

    protected String mask;

    protected String primaryDNS;

    protected String secondaryDNS;

    protected String sufixDNS;

    protected boolean configureGateway;

    public String getGateway()
    {
        return gateway;
    }

    public void setGateway(final String value)
    {
        this.gateway = value;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(final String ip)
    {
        // TODO validate
        this.ip = ip;
    }

    public String getMacAddress()
    {
        return macAddress;
    }

    public void setMacAddress(final String macAddress)
    {
        // TODO validate
        this.macAddress = macAddress;
    }

    public String getLeaseName()
    {
        return leaseName;
    }

    public void setLeaseName(final String leaseName)
    {
        this.leaseName = leaseName;
    }

    public boolean isConfigureGateway()
    {
        return configureGateway;
    }

    public void setConfigureGateway(final boolean configureGateway)
    {
        this.configureGateway = configureGateway;
    }

    public String getMask()
    {
        return mask;
    }

    public void setMask(final String value)
    {
        this.mask = value;
    }

    public String getPrimaryDNS()
    {
        return primaryDNS;
    }

    public void setPrimaryDNS(final String value)
    {
        this.primaryDNS = value;
    }

    public String getSecondaryDNS()
    {
        return secondaryDNS;
    }

    public void setSecondaryDNS(final String value)
    {
        this.secondaryDNS = value;
    }

    public String getSufixDNS()
    {
        return sufixDNS;
    }

    public void setSufixDNS(final String value)
    {
        this.sufixDNS = value;
    }
}
