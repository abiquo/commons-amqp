/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.bpm;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.abiquo.commons.amqp.impl.bpm.domain.BPMRequest;
import com.abiquo.commons.amqp.impl.bpm.domain.BPMResponse;
import com.abiquo.commons.amqp.impl.bpm.domain.job.AbstractBPMJob;
import com.abiquo.commons.amqp.impl.bpm.domain.job.DiskConversionJob;
import com.abiquo.commons.amqp.impl.bpm.domain.job.DiskToVolumeJob;
import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterNotification;

public class SerializationTest
{
    @Test
    public void test_BPMRequestInheritance()
    {
        DiskToVolumeJob statefulRequest = new DiskToVolumeJob("", "", 22L);
        DiskConversionJob conversion = new DiskConversionJob("", "", "", "");

        BPMRequest req = new BPMRequest();
        req.addJob(conversion);
        req.addJob(statefulRequest);
        String serialization = new String(req.toByteArray());

        BPMRequest deserialization = BPMRequest.fromByteArray(serialization.getBytes());
        Assert.assertNotNull(deserialization);
        Assert.assertTrue(deserialization instanceof BPMRequest);

        AbstractBPMJob job1 = req.getJobs().get(0);
        Assert.assertTrue(job1 instanceof DiskConversionJob);
        Assert.assertFalse(job1 instanceof DiskToVolumeJob);

        AbstractBPMJob job2 = req.getJobs().get(1);
        Assert.assertTrue(job2 instanceof DiskToVolumeJob);
        Assert.assertFalse(job2 instanceof DiskConversionJob);
    }

    @Test
    public void test_BPMResponse()
    {
        BPMResponse response = new BPMResponse();
        String serialization = new String(response.toByteArray());

        DatacenterNotification deserialization =
            DatacenterNotification.fromByteArray(serialization.getBytes());
        Assert.assertNotNull(deserialization);
        Assert.assertTrue(deserialization instanceof BPMResponse);
    }
}
