/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter;

import com.abiquo.rsmodel.amqp.datacenter.DatacenterNotification;

public interface DatacenterNotificationCallback
{
    public void onNotification(DatacenterNotification notification);
}
