/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.bpm.domain.job;

public class VolumeToDiskJob extends AbstractBPMJob
{
    private String storagePoolTarget;

    private String diskPath;

    private Long volumeSize;

    public VolumeToDiskJob()
    {
        super(BPMJobType.DUMP_VOLUME_TO_DISK);
    }

    public VolumeToDiskJob(final String storagePoolTarget, final String diskPath,
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
