/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino;

import static com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition.CONFIGURE;
import static com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition.DECONFIGURE;
import static com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition.PAUSE;
import static com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition.POWEROFF;
import static com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition.POWERON;
import static com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition.RECONFIGURE;
import static com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition.RESET;
import static com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition.RESUME;
import static com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition.SNAPSHOT;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.Test;

import com.abiquo.commons.amqp.impl.tarantino.domain.State;
import com.abiquo.commons.amqp.impl.tarantino.domain.StateTransition;

public class StateTransitionTest
{
    @Test
    public void test_availableTransitions()
    {
        Set<StateTransition> transactions = new HashSet<StateTransition>();

        transactions.add(StateTransition.CONFIGURE);
        transactions.add(StateTransition.DECONFIGURE);
        transactions.add(StateTransition.PAUSE);
        transactions.add(StateTransition.POWEROFF);
        transactions.add(StateTransition.POWERON);
        transactions.add(StateTransition.RECONFIGURE);
        transactions.add(StateTransition.RESET);
        transactions.add(StateTransition.RESUME);
        transactions.add(StateTransition.SNAPSHOT);
        transactions.add(StateTransition.ALLOCATE);
        transactions.add(StateTransition.DEALLOCATE);

        assertEquals(StateTransition.values().length, 11);

        for (StateTransition transaction : StateTransition.values())
        {
            assertTrue(transactions.contains(transaction));
        }
    }

    @Test
    public void test_configure()
    {
        assertEquals(CONFIGURE.getEndState(), State.CONFIGURED);
        assertTrue(CONFIGURE.isValidOrigin(State.ALLOCATED));
        assertFalse(CONFIGURE.isValidOrigin(State.ON));
        assertFalse(CONFIGURE.isValidOrigin(State.OFF));
        assertFalse(CONFIGURE.isValidOrigin(State.UNKNOWN));
        assertFalse(CONFIGURE.isValidOrigin(State.PAUSED));
        assertFalse(CONFIGURE.isValidOrigin(State.CONFIGURED));
    }

    @Test
    public void test_reconfigure()
    {
        assertEquals(RECONFIGURE.getEndState(), State.CONFIGURED);
        assertTrue(RECONFIGURE.isValidOrigin(State.OFF));
        assertFalse(RECONFIGURE.isValidOrigin(State.ALLOCATED));
        assertFalse(RECONFIGURE.isValidOrigin(State.ON));
        assertFalse(RECONFIGURE.isValidOrigin(State.UNKNOWN));
        assertFalse(RECONFIGURE.isValidOrigin(State.PAUSED));
        assertFalse(RECONFIGURE.isValidOrigin(State.CONFIGURED));
    }

    @Test
    public void test_deconfigure()
    {
        assertEquals(DECONFIGURE.getEndState(), State.ALLOCATED);
        assertTrue(DECONFIGURE.isValidOrigin(State.CONFIGURED));
        assertFalse(DECONFIGURE.isValidOrigin(State.ON));
        assertFalse(DECONFIGURE.isValidOrigin(State.OFF));
        assertFalse(DECONFIGURE.isValidOrigin(State.ALLOCATED));
        assertFalse(DECONFIGURE.isValidOrigin(State.UNKNOWN));
        assertFalse(DECONFIGURE.isValidOrigin(State.PAUSED));
    }

    @Test
    public void test_powerOn()
    {
        assertEquals(POWERON.getEndState(), State.ON);
        assertTrue(POWERON.isValidOrigin(State.OFF));
        assertTrue(POWERON.isValidOrigin(State.CONFIGURED));
        assertFalse(POWERON.isValidOrigin(State.ON));
        assertFalse(POWERON.isValidOrigin(State.ALLOCATED));
        assertFalse(POWERON.isValidOrigin(State.UNKNOWN));
        assertFalse(POWERON.isValidOrigin(State.PAUSED));
    }

    @Test
    public void test_powerOff()
    {
        assertEquals(POWEROFF.getEndState(), State.OFF);
        assertTrue(POWEROFF.isValidOrigin(State.ON));
        assertFalse(POWEROFF.isValidOrigin(State.OFF));
        assertFalse(POWEROFF.isValidOrigin(State.ALLOCATED));
        assertFalse(POWEROFF.isValidOrigin(State.UNKNOWN));
        assertFalse(POWEROFF.isValidOrigin(State.PAUSED));
        assertFalse(POWEROFF.isValidOrigin(State.CONFIGURED));
    }

    @Test
    public void test_reset()
    {
        assertEquals(RESET.getEndState(), State.ON);
        assertTrue(RESET.isValidOrigin(State.ON));
        assertFalse(RESET.isValidOrigin(State.OFF));
        assertFalse(RESET.isValidOrigin(State.ALLOCATED));
        assertFalse(RESET.isValidOrigin(State.UNKNOWN));
        assertFalse(RESET.isValidOrigin(State.PAUSED));
        assertFalse(RESET.isValidOrigin(State.CONFIGURED));
    }

    @Test
    public void test_pause()
    {
        assertEquals(PAUSE.getEndState(), State.PAUSED);
        assertTrue(PAUSE.isValidOrigin(State.ON));
        assertFalse(PAUSE.isValidOrigin(State.OFF));
        assertFalse(PAUSE.isValidOrigin(State.ALLOCATED));
        assertFalse(PAUSE.isValidOrigin(State.UNKNOWN));
        assertFalse(PAUSE.isValidOrigin(State.PAUSED));
        assertFalse(PAUSE.isValidOrigin(State.CONFIGURED));
    }

    @Test
    public void test_resume()
    {
        assertEquals(RESUME.getEndState(), State.ON);
        assertTrue(RESUME.isValidOrigin(State.PAUSED));
        assertFalse(RESUME.isValidOrigin(State.ON));
        assertFalse(RESUME.isValidOrigin(State.OFF));
        assertFalse(RESUME.isValidOrigin(State.ALLOCATED));
        assertFalse(RESUME.isValidOrigin(State.UNKNOWN));
        assertFalse(RESUME.isValidOrigin(State.CONFIGURED));
    }

    @Test
    public void test_snapshot()
    {
        assertEquals(SNAPSHOT.getEndState(), State.OFF);
        assertTrue(SNAPSHOT.isValidOrigin(State.OFF));
        assertFalse(SNAPSHOT.isValidOrigin(State.ON));
        assertFalse(SNAPSHOT.isValidOrigin(State.ALLOCATED));
        assertFalse(SNAPSHOT.isValidOrigin(State.UNKNOWN));
        assertFalse(SNAPSHOT.isValidOrigin(State.PAUSED));
        assertFalse(SNAPSHOT.isValidOrigin(State.CONFIGURED));
    }
}
