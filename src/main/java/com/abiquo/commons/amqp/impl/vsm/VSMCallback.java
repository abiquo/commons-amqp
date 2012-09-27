/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.vsm;

import com.abiquo.rsmodel.amqp.vsm.VirtualSystemEvent;

public interface VSMCallback
{
    public void onEvent(VirtualSystemEvent message);
}
