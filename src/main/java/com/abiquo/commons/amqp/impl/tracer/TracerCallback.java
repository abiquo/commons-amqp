/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tracer;

import com.abiquo.commons.amqp.impl.tracer.domain.Trace;

public interface TracerCallback
{
    public void onTrace(Trace trace);
}
