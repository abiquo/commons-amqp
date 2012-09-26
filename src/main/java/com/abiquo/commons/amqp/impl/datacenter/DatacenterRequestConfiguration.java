/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter;

import java.io.IOException;

import com.abiquo.commons.amqp.config.DefaultConfiguration;
import com.rabbitmq.client.Channel;

public class DatacenterRequestConfiguration extends DefaultConfiguration
{
    private String datacenterId;

    private RequestType type;

    private static final String DATACENTER_EXCHANGE = "abiquo.datacenter.requests";

    private static final String JOBS_ROUTING_KEY = "abiquo.datacenter.requests";

    private static final String JOBS_QUEUE = "abiquo.datacenter.requests";

    public enum RequestType
    {
        VIRTUAL_FACTORY("virtualfactory"), BPM("bpm");

        public String internalName;

        private RequestType(final String internalName)
        {
            this.internalName = internalName;
        }
    };

    public static String getDatacenterExchange()
    {
        return DATACENTER_EXCHANGE;
    }

    public static String buildJobsRoutingKey(final String datacenterId, final RequestType type)
    {
        return JOBS_ROUTING_KEY.concat(".").concat(datacenterId).concat(".")
            .concat(type.internalName);
    }

    public static String buildJobsQueue(final String datacenterId, final RequestType type)
    {
        return JOBS_QUEUE.concat(".").concat(datacenterId).concat(".").concat(type.internalName);
    }

    public DatacenterRequestConfiguration(final String datacenterId, final RequestType type)
    {
        this.datacenterId = datacenterId;
        this.type = type;
    }

    @Override
    public void declareExchanges(Channel channel) throws IOException
    {
        channel.exchangeDeclare(getDatacenterExchange(), TopicExchange, Durable);
    }

    @Override
    public void declareQueues(Channel channel) throws IOException
    {
        channel.queueDeclare(buildJobsQueue(datacenterId, type), Durable, NonExclusive,
            NonAutodelete, null);
        channel.queueBind(buildJobsQueue(datacenterId, type), getDatacenterExchange(),
            buildJobsRoutingKey(datacenterId, type));
    }
}
