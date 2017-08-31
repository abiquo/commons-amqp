/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.exception;

public class SSLException extends Exception
{
    private static final long serialVersionUID = 2700577308583950195L;

    public SSLException(final Exception exception)
    {
        super(exception);
    }
}
