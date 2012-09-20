/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

public class DiskSnapshot extends DiskStandard
{
    protected String snapshotFilename;

    protected String name;

    public String getSnapshotFilename()
    {
        return snapshotFilename;
    }

    public void setSnapshotFilename(String filename)
    {
        this.snapshotFilename = filename;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
