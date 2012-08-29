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

package com.abiquo.commons.amqp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

/**
 * A collection of helper methods to wrap the use of Jackson.
 * 
 * @author eruiz@abiquo.com
 */
public class JSONUtils
{
    private final static Logger LOGGER = LoggerFactory.getLogger(JSONUtils.class);

    /**
     * Serializes the given Object to JSON and returns the result in array of bytes.
     * 
     * @param value The object to serialize.
     * @return A "" array representing the JSON serialization of the object. A null value if the
     *         serialization fails.
     */
    public static byte[] serialize(final Object value)
    {
        // ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper = new XmlMapper();
        JaxbAnnotationModule jaxbModule = new JaxbAnnotationModule();
        mapper.registerModule(jaxbModule);

        try
        {
            return mapper.writeValueAsBytes(value);
        }
        catch (Exception e)
        {
            LOGGER.error(String.format("Can not serialize %s to byte array.", value.getClass()
                .getSimpleName()), e);
            return null;
        }
    }

    /**
     * Deserializes the given byte array JSON serialization.
     * 
     * @param bytes A byte array representing the JSON serialization of an object.
     * @param type The Class of the object to deserialize
     * @return The deserialized object or null if the process fails.
     */
    public static <T> T deserialize(final byte[] bytes, final Class<T> type)
    {
        // ObjectMapper mapper = new ObjectMapper();
        ObjectMapper mapper = new XmlMapper();
        JaxbAnnotationModule jaxbModule = new JaxbAnnotationModule();
        mapper.registerModule(jaxbModule);

        String content = new String(bytes);

        try
        {
            return mapper.readValue(content, type);
        }
        catch (Exception e)
        {
            LOGGER.error(
                String.format("Can not deserialize %s from byte array.", type.getSimpleName()), e);
            return null;
        }
    }
}
