/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.manual;

import java.io.IOException;

import org.testng.annotations.Test;

import com.abiquo.commons.amqp.impl.vsm.VSMCallback;
import com.abiquo.commons.amqp.impl.vsm.VSMConfiguration;
import com.abiquo.commons.amqp.impl.vsm.VSMConsumer;
import com.abiquo.commons.amqp.impl.vsm.VSMProducer;
import com.abiquo.commons.amqp.impl.vsm.domain.VirtualSystemEvent;

public class VSMManualTest
{
    public static void main(String[] args) throws IOException
    {
        new VSMManualTest().basic();
    }

    @Test(enabled = false)
    public void basic() throws IOException
    {
        VSMConsumer consumer = new VSMConsumer(VSMConfiguration.EVENT_SYNK_QUEUE);

        consumer.addCallback(new VSMCallback()
        {
            @Override
            public void onEvent(VirtualSystemEvent message)
            {
                System.out.println(message.toString());
            }
        });

        consumer.start();

        VSMProducer p = createProducer();
        p.openChannel();

        for (int i = 0; i < 10; i++)
        {
            p.publish(new VirtualSystemEvent("", "vmx-04", "@", "POWERED_ON"));

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        p.closeChannel();
        consumer.stop();
    }

    /**
     * MOVEs between PMs Test environment set with 2PMs (ESXi) and 3VMs faking MOVE events to
     * overload rabbit and force unresponsive server. More info at
     * 
     * @throws IOException
     */
    @Test(enabled = false)
    public void stressTest() throws IOException
    {
        final String[] vMachines2Move =
            new String[] {"ABQ_3653ff3a-6d84-4615-8b3e-9c649f54e74c",
            "ABQ_a95fbe29-b062-4e9a-a3c4-bb64a7221cd2", "ABQ_deb8c48a-7b83-48a7-bb86-fc77702ce2d5"};

        final String HYPERVISOR1 = "http://10.60.20.70:8080";
        final String HYPERVISOR2 = "http://10.60.20.71:8080";

        VSMConsumer consumer = new VSMConsumer(VSMConfiguration.EVENT_SYNK_QUEUE);

        consumer.addCallback(new VSMCallback()
        {
            @Override
            public void onEvent(VirtualSystemEvent message)
            {
                System.out.println(message.toString());
            }
        });

        consumer.start();

        VSMProducer p = createProducer();
        p.openChannel();

        for (int i = 0; i < 30; i++)
        {
            p.publish(new VirtualSystemEvent(vMachines2Move[0], "VMX_04", HYPERVISOR1, "MOVED"));

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            p.publish(new VirtualSystemEvent(vMachines2Move[1], "VMX_04", HYPERVISOR2, "MOVED"));

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            p.publish(new VirtualSystemEvent(vMachines2Move[2], "VMX_04", HYPERVISOR1, "MOVED"));

            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        p.closeChannel();
        consumer.stop();
    }

    @Test(enabled = false)
    public void single() throws IOException
    {
        VSMProducer p = createProducer();
        p.openChannel();
        p.publish(null); // new VirtualSystemEvent("", "vmx-04", "@", "POWERED_ON"));
        p.closeChannel();
    }

    protected VSMProducer createProducer() throws IOException
    {
        return new VSMProducer();
    }

    protected VSMConsumer createConsumer(String queue) throws IOException
    {
        return new VSMConsumer(queue);
    }
}
