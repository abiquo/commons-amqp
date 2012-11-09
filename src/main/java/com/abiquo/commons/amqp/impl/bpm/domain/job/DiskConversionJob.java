/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.bpm.domain.job;


public class DiskConversionJob extends AbstractBPMJob
{
    private String diskPathSource;

    private String diskPathDestination;

    private String sourceFormat;

    private String destinationFormat;

    private boolean destroyOriginDisk;

    public DiskConversionJob()
    {
        super(BPMJobType.DISK_CONVERSION);
    }

    public DiskConversionJob(final String sourcePath, final String destinationPath,
        final String sourceFormat, final String destinationFormat)
    {
        this();
        this.diskPathSource = sourcePath;
        this.diskPathDestination = destinationPath;
        this.sourceFormat = sourceFormat;
        this.destinationFormat = destinationFormat;
    }

    public String getDiskPathSource()
    {
        return diskPathSource;
    }

    public void setDiskPathSource(final String diskPathSource)
    {
        this.diskPathSource = diskPathSource;
    }

    public String getDiskPathDestination()
    {
        return diskPathDestination;
    }

    public void setDiskPathDestination(final String diskPathDestination)
    {
        this.diskPathDestination = diskPathDestination;
    }

    public String getSourceFormat()
    {
        return sourceFormat;
    }

    public void setSourceFormat(final String sourceFormat)
    {
        this.sourceFormat = sourceFormat;
    }

    public String getDestinationFormat()
    {
        return destinationFormat;
    }

    public void setDestinationFormat(final String destinationFormat)
    {
        this.destinationFormat = destinationFormat;
    }

    public boolean isDestroyOriginDisk()
    {
        return destroyOriginDisk;
    }

    public void setDestroyOriginDisk(final boolean destroyOriginDisk)
    {
        this.destroyOriginDisk = destroyOriginDisk;
    }

}
