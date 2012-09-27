/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino;

import com.abiquo.rsmodel.amqp.tarantino.dto.TarantinoResponse;

public interface TarantinoResponseCallback
{
    public void onResponse(TarantinoResponse response);
}
