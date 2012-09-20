/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

/**
 * The states that a virtual machine can have.
 */
public enum State
{
    ON, OFF, PAUSED, ALLOCATED, CONFIGURED, UNKNOWN, NOT_ALLOCATED, LOCKED;

    public static State fromValue(final String value)
    {
        return State.valueOf(value.toUpperCase());
    }

    public State travel(final StateTransition transaction)
    {
        if (!transaction.isValidOrigin(this))
        {
            throw new RuntimeException("Invalid origin " + this + " for transaction " + transaction);
        }

        return transaction.getEndState();
    }
}
