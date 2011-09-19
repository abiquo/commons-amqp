/**
 * Abiquo community edition
 * cloud management application for hybrid clouds
 * Copyright (C) 2008-2010 - Abiquo Holdings S.L.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package com.abiquo.commons.amqp.impl.datacenter.domain;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

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
        states.add(State.UNDEPLOYED);
        states.add(State.UNKNOWN);

        assertEquals(State.values().length, 6);

        for (State state : State.values())
        {
            assertTrue(states.contains(state));
        }
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void test_invalidTravel()
    {
        State.UNKNOWN.travel(StateTransaction.CONFIGURE);
    }

    @Test
    public void test_configureTravel()
    {
        assertEquals(State.UNDEPLOYED.travel(StateTransaction.CONFIGURE), State.CONFIGURED);
    }

    @Test
    public void test_reconfigureTravel()
    {
        assertEquals(State.OFF.travel(StateTransaction.RECONFIGURE), State.CONFIGURED);
    }

    @Test
    public void test_deconfigureTravel()
    {
        assertEquals(State.CONFIGURED.travel(StateTransaction.DECONFIGURE), State.UNDEPLOYED);
    }

    @Test
    public void test_powerOnTravel()
    {
        assertEquals(State.OFF.travel(StateTransaction.POWERON), State.ON);
        assertEquals(State.CONFIGURED.travel(StateTransaction.POWERON), State.ON);
    }

    @Test
    public void test_powerOffTravel()
    {
        assertEquals(State.ON.travel(StateTransaction.POWEROFF), State.OFF);
    }

    @Test
    public void test_resetTravel()
    {
        assertEquals(State.ON.travel(StateTransaction.RESET), State.ON);
    }

    @Test
    public void test_pauseTravel()
    {
        assertEquals(State.ON.travel(StateTransaction.PAUSE), State.PAUSED);
    }

    @Test
    public void test_resumeTravel()
    {
        assertEquals(State.PAUSED.travel(StateTransaction.RESUME), State.ON);
    }

    @Test
    public void test_snapshotTravel()
    {
        assertEquals(State.OFF.travel(StateTransaction.SNAPSHOT), State.OFF);
    }
}
