/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

import static com.abiquo.commons.amqp.impl.tarantino.domain.State.ALLOCATED;
import static com.abiquo.commons.amqp.impl.tarantino.domain.State.CONFIGURED;
import static com.abiquo.commons.amqp.impl.tarantino.domain.State.NOT_ALLOCATED;
import static com.abiquo.commons.amqp.impl.tarantino.domain.State.OFF;
import static com.abiquo.commons.amqp.impl.tarantino.domain.State.ON;
import static com.abiquo.commons.amqp.impl.tarantino.domain.State.PAUSED;
import static java.util.Collections.singleton;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Valid transactions between {@link State}.
 */
public enum StateTransition
{
    // Configure transition
    CONFIGURE(singleton(ALLOCATED), CONFIGURED),

    // Reconfigure transition
    RECONFIGURE(singleton(OFF), CONFIGURED),

    // Deconfigure transition
    DECONFIGURE(singleton(CONFIGURED), ALLOCATED),

    // PowerOn transition
    POWERON(new HashSet<State>(Arrays.asList(CONFIGURED, OFF)), ON),

    // PowerOff transition
    POWEROFF(singleton(ON), OFF),

    // Reset transition
    RESET(singleton(ON), ON),

    // Pause transition
    PAUSE(singleton(ON), PAUSED),

    // Resume transition
    RESUME(singleton(PAUSED), ON),

    // Snapshot transition
    SNAPSHOT(singleton(OFF), OFF),

    // Not allocated yet
    ALLOCATE(singleton(NOT_ALLOCATED), ALLOCATED),

    // Exists the machine in Abiquo, and has hypervisor assigned, but does not exists in hypervisor
    DEALLOCATE(singleton(ALLOCATED), NOT_ALLOCATED);

    private Set<State> origins;

    private State end;

    private StateTransition(final Set<State> origins, final State end)
    {
        this.origins = origins;
        this.end = end;
    }

    public State getEndState()
    {
        return this.end;
    }

    public boolean isValidOrigin(final State origin)
    {
        return this.origins.contains(origin);
    }

    public static StateTransition fromValue(final String value)
    {
        return StateTransition.valueOf(value.toUpperCase());
    }

    /** Inverse Transition */
    public static StateTransition rollback(final StateTransition s)
    {
        switch (s)
        {
            case CONFIGURE:
                return DECONFIGURE;

            case DECONFIGURE:
                return CONFIGURE;

            case POWEROFF:
                return POWERON;

            case POWERON:
                return POWEROFF;

            case PAUSE:
                return RESUME;

            case RESUME:
                return PAUSE;

            default: // RESET, RECONFIGURE and SNPASHOT (starts and ends in the same state)
                return s;
        }
    }
}
