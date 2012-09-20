/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain.dto;

public interface JobIdentifier
{

    public abstract String getId();

    public abstract void setId(String id);

    public abstract String getParent();

    public abstract boolean isRoot();

    public abstract int level();

    public abstract String getParentAt(final int level);

}
