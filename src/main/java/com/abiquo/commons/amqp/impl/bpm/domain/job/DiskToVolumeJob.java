/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
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

    public void setStoragePoolTarget(final String storagePoolTarget)
    {
        this.storagePoolTarget = storagePoolTarget;
    }

    public String getDiskPath()
    {
        return diskPath;
    }

    public void setDiskPath(final String diskPath)
    {
        this.diskPath = diskPath;
    }

    public Long getVolumeSize()
    {
        return volumeSize;
    }

    public void setVolumeSize(final Long volumeSize)
    {
        this.volumeSize = volumeSize;
    }
}
