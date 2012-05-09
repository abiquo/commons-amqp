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

import java.util.Set;

import com.abiquo.commons.amqp.consumer.RequestBasedCallback;
import com.abiquo.commons.amqp.impl.bpm.domain.BPMJob;
import com.abiquo.commons.amqp.impl.bpm.domain.BPMRequest;
import com.abiquo.commons.amqp.impl.bpm.domain.ImageConverterRequest;
import com.abiquo.commons.amqp.impl.bpm.domain.StatefulDiskRequest;
import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConfiguration.RequestType;
import com.abiquo.commons.amqp.impl.datacenter.DatacenterRequestConsumer;
import com.abiquo.commons.amqp.impl.datacenter.domain.DatacenterRequest;
import com.rabbitmq.client.Envelope;

public class BPMRequestConsumer extends DatacenterRequestConsumer
{
    public BPMRequestConsumer(final String datacenterId)
    {
        super(datacenterId, RequestType.BPM);
    }

    @Override
    protected DatacenterRequest deserializeRequest(final Envelope envelope, final byte[] body)
    {
        return BPMRequest.fromByteArray(body);
    }

    @Override
    protected void consume(final DatacenterRequest request,
        final Set<RequestBasedCallback> callbacks)
    {
        if (request instanceof BPMRequest)
        {
            for (BPMJob job : ((BPMRequest) request).getJobs())
            {
                if (job instanceof ImageConverterRequest)
                {
                    Set<RequestBasedCallback> jobCallbacks =
                        callbacksMap.get(ImageConverterRequest.class);
                    consume((ImageConverterRequest) job, jobCallbacks);
                    // TODO ACK JOB
                }
                else if (job instanceof StatefulDiskRequest)
                {
                    Set<RequestBasedCallback> jobCallbacks =
                        callbacksMap.get(StatefulDiskRequest.class);
                    consume((StatefulDiskRequest) job, jobCallbacks);
                    // TODO ACK JOB
                }
            }
            // TODO ACK TASK
        }
    }

    protected void consume(final ImageConverterRequest request,
        final Set<RequestBasedCallback> callbacks)
    {
        for (RequestBasedCallback callback : callbacks)
        {
            ImageConverterRequestCallback realCallback = (ImageConverterRequestCallback) callback;
            realCallback.convertDisk(request);
        }
    }

    protected void consume(final StatefulDiskRequest request,
        final Set<RequestBasedCallback> callbacks)
    {
        for (RequestBasedCallback callback : callbacks)
        {
            StatefulDiskRequestCallback realCallback = (StatefulDiskRequestCallback) callback;

            switch (request.getType())
            {
                case DUMP_DISK_TO_VOLUME:
                    realCallback.dumpDiskToVolume(request);
                    break;

                case DUMP_VOLUME_TO_DISK:
                    realCallback.dumpVolumeToDisk(request);
                    break;
            }
        }
    }

}
