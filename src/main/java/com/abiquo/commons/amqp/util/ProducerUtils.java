/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.util;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;

/**
 * A collection of helper methods to be used by the producers.
 * 
 * @author eruiz@abiquo.com
 */
public class ProducerUtils
{
    /**
     * Publish a message NonMandatory and NonImmediate.
     * 
     * @param channel The AMQ Channel to use.
     * @param exchange The exchange to publish the message to
     * @param routingKey The routing key
     * @param body The message, not null!
     * @throws IOException If an error is encountered
     */
    public static void publishPersistentText(Channel channel, String exchange, String routingKey,
        byte[] body) throws IOException
    {
        channel.basicPublish(exchange, routingKey, MessageProperties.PERSISTENT_TEXT_PLAIN, body);
    }
}
