/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class DiskDescription
{

    // TODO sequence and remove SecondaryDisk{Standard/Stateful} classes

    /**
     * Identify the controller (X:_)
     */
    protected Integer busNumber;

    /**
     * Identify the disk inside the controller (_:X)
     */
    protected Integer unitNumber;

    public enum DiskControllerType
    {
        SCSI, IDE
    }

    protected DiskFormatType format;

    protected long capacityInBytes;

    /**
     * Datastore (rootPath + directory) where the virtualmachine is booted. VirtualMachine UUID is
     * added to build the complete destination path. Ex: /var/lib/virt
     */
    protected String destinationDatastore;

    protected DiskControllerType diskControllerType;

    public DiskFormatType getFormat()
    {
        return format;
    }

    public void setFormat(final DiskFormatType format)
    {
        this.format = format;
    }

    public long getCapacityInBytes()
    {
        return capacityInBytes;
    }

    public void setCapacityInBytes(final long capacityInBytes)
    {
        this.capacityInBytes = capacityInBytes;
    }

    public String getDestinationDatastore()
    {
        return destinationDatastore;
        // .endsWith("/") ? destinationDatastore
        // : destinationDatastore + '/';
    }

    public void setDestinationDatastore(final String destinationDatastore)
    {
        this.destinationDatastore = destinationDatastore;
    }

    public DiskControllerType getDiskControllerType()
    {
        return diskControllerType;
    }

    public void setDiskControllerType(final DiskControllerType diskControllerType)
    {
        this.diskControllerType = diskControllerType;
    }

    @JsonIgnore
    public boolean isDiskControllerTypeSet()
    {
        return this.diskControllerType != null;
    }

    public Integer getBusNumber()
    {
        return busNumber;
    }

    public void setBusNumber(Integer busNumber)
    {
        this.busNumber = busNumber;
    }

    public Integer getUnitNumber()
    {
        return unitNumber;
    }

    public void setUnitNumber(Integer unitNumber)
    {
        this.unitNumber = unitNumber;
    }

    @JsonIgnore
    public void setBusAndUnitNumber(Integer busNumber, Integer unitNumber)
    {
        setBusNumber(busNumber);
        setUnitNumber(unitNumber);
    }

    /** ######## DiskFomratType already in the *model* project TODO duplicated ######## **/

    public enum DiskFormatType
    {
        UNKNOWN("raw"),

        RAW("raw"),

        INCOMPATIBLE("raw"),

        VMDK_STREAM_OPTIMIZED("raw"),

        VMDK_FLAT("raw"),

        VMDK_SPARSE("vmdk"),

        VHD_FLAT("raw"),

        VHD_SPARSE("raw"),

        VDI_FLAT("raw"),

        VDI_SPARSE("raw"),

        QCOW2_FLAT("qcow2"),

        QCOW2_SPARSE("qcow2");

        private String libvirtFormat;

        public boolean isSparse()
        {
            return this == VMDK_SPARSE //
                || this == VHD_SPARSE //
                || this == VDI_SPARSE //
                || this == QCOW2_SPARSE;
        }

        private DiskFormatType(final String libvirtFormat)
        {
            this.libvirtFormat = libvirtFormat;
        }

        public String getLibvirtFormat()
        {
            return libvirtFormat;
        }
    }
}
