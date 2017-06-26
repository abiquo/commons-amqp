/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.abiquo.commons.amqp.AMQPProperties;
import com.rabbitmq.client.Address;
import com.rabbitmq.client.AddressResolver;

public class SystemPropertyAddressResolver implements AddressResolver
{
    private final List<Address> addresses = AMQPProperties.getAddresses();

    public SystemPropertyAddressResolver()
    {
        Collections.shuffle(addresses);
    }

    @Override
    public List<Address> getAddresses() throws IOException
    {
        return addresses;
    }
}
