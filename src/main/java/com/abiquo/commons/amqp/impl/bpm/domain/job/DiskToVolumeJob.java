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

public class DiskToVolumeJob extends AbstractBPMJob
{
    private String storagePoolTarget;

    private String diskPath;

    private Long volumeSize;

    public DiskToVolumeJob()
    {
        super(BPMJobType.DUMP_DISK_TO_VOLUME);
    }

    public DiskToVolumeJob(final String storagePoolTarget, final String diskPath,
        final Long volumeSize)
    {
        this();
        this.storagePoolTarget = storagePoolTarget;
        this.diskPath = diskPath;
        this.volumeSize = volumeSize;

    }

    public String getStoragePoolTarget()
    {
        return storagePoolTarget;
    }

    public void setStoragePoolTarget(String storagePoolTarget)
    {
        this.storagePoolTarget = storagePoolTarget;
    }

    public String getDiskPath()
    {
        return diskPath;
    }

    public void setDiskPath(String diskPath)
    {
        this.diskPath = diskPath;
    }

    public Long getVolumeSize()
    {
        return volumeSize;
    }

    public void setVolumeSize(Long volumeSize)
    {
        this.volumeSize = volumeSize;
    }
}
