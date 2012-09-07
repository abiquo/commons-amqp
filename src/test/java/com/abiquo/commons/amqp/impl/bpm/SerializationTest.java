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
package com.abiquo.commons.amqp.impl.bpm;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.abiquo.rsmodel.amqp.bpm.AbstractBPMJob;
import com.abiquo.rsmodel.amqp.bpm.BPMRequest;
import com.abiquo.rsmodel.amqp.bpm.BPMResponse;
import com.abiquo.rsmodel.amqp.bpm.DiskConversionJob;
import com.abiquo.rsmodel.amqp.bpm.DiskToVolumeJob;
import com.abiquo.rsmodel.amqp.datacenter.DatacenterNotification;

public class SerializationTest
{
    @Test(enabled = false)
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
