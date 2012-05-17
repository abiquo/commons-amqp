/**
 * Abiquo community edition
 * cloud management application for hybrid clouds
 * Copyright (C) 2008-2010 - Abiquo Holdings S.L.
 * 
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
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

    public DiskConversionJob(final String sourcePath, final String destinationPath, final String sourceFormat,
        final String destinationFormat)
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

    public boolean mustDestroyOriginDisk()
    {
        return destroyOriginDisk;
    }

    public void setDestroyOriginDisk(final boolean destroyOriginDisk)
    {
        this.destroyOriginDisk = destroyOriginDisk;
    }

}
