/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.nodecollector;

import com.abiquo.rsmodel.amqp.nodecollector.NodeCollectorNotification;

public interface NodeCollectorNotificationCallback
{
    public void onNotification(NodeCollectorNotification notification);
}
