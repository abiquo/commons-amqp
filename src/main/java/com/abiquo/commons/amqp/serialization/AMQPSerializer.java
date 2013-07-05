/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.serialization;

import java.io.IOException;
import java.io.Serializable;

public interface AMQPSerializer<T extends Serializable>
{
    public byte[] serialize(final T value) throws IOException;
}
