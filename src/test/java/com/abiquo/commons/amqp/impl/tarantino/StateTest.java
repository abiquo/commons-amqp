/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import com.abiquo.commons.amqp.impl.tarantino.domain.State;
import com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition;

public class StateTest
{
    @Test
    public void test_availableStates()
    {
        Set<State> states = new HashSet<State>();

        states.add(State.CONFIGURED);
        states.add(State.OFF);
        states.add(State.ON);
        states.add(State.PAUSED);
        states.add(State.ALLOCATED);
        states.add(State.UNKNOWN);
        states.add(State.LOCKED);
        states.add(State.NOT_ALLOCATED);
        assertEquals(State.values().length, 8);

        for (State state : State.values())
        {
            assertTrue(states.contains(state));
        }
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void test_invalidTravel()
    {
        State.UNKNOWN.travel(StateTransition.CONFIGURE);
    }

    @Test
    public void test_configureTravel()
    {
        assertEquals(State.ALLOCATED.travel(StateTransition.CONFIGURE), State.CONFIGURED);
    }

    @Test
    public void test_reconfigureTravel()
    {
        assertEquals(State.OFF.travel(StateTransition.RECONFIGURE), State.CONFIGURED);
    }

    @Test
    public void test_deconfigureTravel()
    {
        assertEquals(State.CONFIGURED.travel(StateTransition.DECONFIGURE), State.ALLOCATED);
    }

    @Test
    public void test_powerOnTravel()
    {
        assertEquals(State.OFF.travel(StateTransition.POWERON), State.ON);
        assertEquals(State.CONFIGURED.travel(StateTransition.POWERON), State.ON);
    }

    @Test
    public void test_powerOffTravel()
    {
        assertEquals(State.ON.travel(StateTransition.POWEROFF), State.OFF);
    }

    @Test
    public void test_resetTravel()
    {
        assertEquals(State.ON.travel(StateTransition.RESET), State.ON);
    }

    @Test
    public void test_pauseTravel()
    {
        assertEquals(State.ON.travel(StateTransition.PAUSE), State.PAUSED);
    }

    @Test
    public void test_resumeTravel()
    {
        assertEquals(State.PAUSED.travel(StateTransition.RESUME), State.ON);
    }

    @Test
    public void test_snapshotTravel()
    {
        assertEquals(State.OFF.travel(StateTransition.SNAPSHOT), State.OFF);
    }
}
