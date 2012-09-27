/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.ha;

import com.abiquo.rsmodel.amqp.ha.HATask;

public interface HACallback
{
    public void executeHighAvailabilityTask(HATask task);
}
