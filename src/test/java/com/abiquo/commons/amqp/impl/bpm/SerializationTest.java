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

import com.abiquo.commons.amqp.impl.bpm.domain.BPMJob;
import com.abiquo.commons.amqp.impl.bpm.domain.BPMJob.TYPE;
import com.abiquo.commons.amqp.impl.bpm.domain.BPMRequest;
import com.abiquo.commons.amqp.impl.bpm.domain.BPMResponse;
import com.abiquo.commons.amqp.impl.bpm.domain.ImageConverterRequest;
import com.abiquo.commons.amqp.impl.bpm.domain.StatefulDiskRequest;
import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterNotification;

public class SerializationTest
{
    @Test
    public void test_BPMRequestInheritance()
    {
        ImageConverterRequest imageRequest = new ImageConverterRequest();
        StatefulDiskRequest statefulRequest =
            new StatefulDiskRequest(1, "", "", 1, 22L, 22, TYPE.DUMP_TO_DISK);
        BPMRequest req = new BPMRequest(BPMRequest.TYPE.PERSISTENT);
        req.addJob(imageRequest);
        req.addJob(statefulRequest);
        String serialization = new String(req.toByteArray());

        BPMRequest deserialization = BPMRequest.fromByteArray(serialization.getBytes());
        Assert.assertNotNull(deserialization);
        Assert.assertTrue(deserialization instanceof BPMRequest);

        BPMJob job1 = req.getJobs().get(0);
        Assert.assertTrue(job1 instanceof ImageConverterRequest);
        Assert.assertFalse(job1 instanceof StatefulDiskRequest);

        BPMJob job2 = req.getJobs().get(1);
        Assert.assertTrue(job2 instanceof StatefulDiskRequest);
        Assert.assertFalse(job2 instanceof ImageConverterRequest);
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
