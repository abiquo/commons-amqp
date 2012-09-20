/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.abiquo.commons.amqp.util.AddressingUtils;

/**
 * @see AddressingUtils
 */
public class DiskStateful extends DiskDescription
{
    /** The name of the disk in the storage device. */
    protected String name;

    /**
     * The location of the disk in the form:
     * 
     * <pre>
     * ip-<ip:port>-iscsi-<iqn>-lun-<lun>-part-<partition>
     * </pre>
     * 
     * You should use the helper methods provided in this class to access concrete information about
     * the location. See {@link AddressingUtils} for details about the format.
     * 
     * @see AddressingUtils
     */
    protected String location;

    @JsonIgnore
    public String getStorageDeviceIp()
    {
        return AddressingUtils.getIP(location);
    }

    @JsonIgnore
    public String getStorageDevicePort()
    {
        return AddressingUtils.getPort(location);
    }

    @JsonIgnore
    public String getIQN()
    {
        return AddressingUtils.getIQN(location);
    }

    @JsonIgnore
    public String getLUN()
    {
        return AddressingUtils.getLUN(location);
    }

    @JsonIgnore
    public String getPartition()
    {
        return AddressingUtils.getPartition(location);
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(final String value)
    {
        this.location = value;
    }

    public String getName()
    {
        return name;
    }

    public void setName(final String name)
    {
        this.name = name;
    }

    @Override
    public DiskFormatType getFormat()
    {
        return DiskFormatType.RAW;
    }
}
