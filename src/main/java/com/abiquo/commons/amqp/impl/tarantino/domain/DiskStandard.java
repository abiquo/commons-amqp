/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

public class DiskStandard extends DiskDescription
{
    /**
     * Datastore repository location (abiquo repository if is managed and local datastore if it is
     * imported). Only used for VBOX. Other hypervisors have a property in abiquo.properties and
     * aim.ini. Ex: nfs-devel:/opt/vm_repository
     */
    protected String repository;

    /** Relative path inside the datastore. Ex: 1/rs.bcn.abiquo.com/nostalgia/nostalgia.vdi */
    protected String path;

    /**
     * The address of the repository manager (usually the Appliance Library itself). This property
     * is used by the XenServer plugin to perform a copy of the disk file between two directories of
     * the repository.
     */
    protected String repositoryManagerAddress;

    protected long diskFileSizeInBytes;

    public String getRepository()
    {
        return repository;
    }

    public void setRepository(final String value)
    {
        this.repository = value;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(final String value)
    {
        this.path = value;
    }

    public String getRepositoryManagerAddress()
    {
        return repositoryManagerAddress;
    }

    public void setRepositoryManagerAddress(final String repositoryManagerAddress)
    {
        this.repositoryManagerAddress = repositoryManagerAddress;
    }

    public long getDiskFileSizeInBytes()
    {
        return diskFileSizeInBytes;
    }

    public void setDiskFileSizeInBytes(final long diskFileSizeInBytes)
    {
        this.diskFileSizeInBytes = diskFileSizeInBytes;
    }
}
