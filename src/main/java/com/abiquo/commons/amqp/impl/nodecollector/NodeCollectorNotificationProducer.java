/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.nodecollector;

import static com.abiquo.commons.amqp.impl.nodecollector.NodeCollectorConfiguration.NODE_COLLECTOR_EXCHANGE;
import static com.abiquo.commons.amqp.impl.nodecollector.NodeCollectorConfiguration.NODE_COLLECTOR_ROUTING_KEY;
import static com.abiquo.commons.amqp.util.ProducerUtils.publishPersistentText;

import java.io.IOException;

import com.abiquo.commons.amqp.producer.BaseProducer;
import com.abiquo.rsmodel.ampq.nodecollector.NodeCollectorNotification;

public class NodeCollectorNotificationProducer extends BaseProducer<NodeCollectorNotification>
{
    public NodeCollectorNotificationProducer()
    {
        super(new NodeCollectorConfiguration());
    }

    @Override
    public void publish(NodeCollectorNotification notification) throws IOException
    {
        publishPersistentText(getChannel(), NODE_COLLECTOR_EXCHANGE, NODE_COLLECTOR_ROUTING_KEY,
            notification.toByteArray());
    }
}
