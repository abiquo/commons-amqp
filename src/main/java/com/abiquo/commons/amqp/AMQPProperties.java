/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import static java.lang.System.getProperty;

import java.util.Arrays;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;

import com.rabbitmq.client.Address;

public class AMQPProperties
{
    public static String getUserName()
    {
        return getProperty("abiquo.rabbitmq.username", "guest");
    }

    public static String getPassword()
    {
        return getProperty("abiquo.rabbitmq.password", "guest");
    }

    public static String getVirtualHost()
    {
        return getProperty("abiquo.rabbitmq.virtualHost", "/");
    }

    public static Integer getConnectionTimeout()
    {
        return Integer.getInteger("abiquo.rabbitmq.connectionTimeout", 0);
    }

    public static Integer getRequestedHeartbeat()
    {
        return Integer.getInteger("abiquo.rabbitmq.requestedHeartbeat", 0);
    }

    public static Integer getNetworkRecoveryInterval()
    {
        return Integer.getInteger("abiquo.rabbitmq.networkRecoveryInterval", 5000);
    }

    public static boolean isMultitenantEnabled()
    {
        return Boolean.getBoolean("abiquo.rabbitmq.multitenant");
    }

    public static List<Address> getAddresses()
    {
        final String value = System.getProperty("abiquo.rabbitmq.addresses", "localhost:5672");
        return Arrays.asList(Address.parseAddresses(value));
    }

    public static boolean isTLSEnabled()
    {
        return Boolean.getBoolean("abiquo.rabbitmq.tls");
    }

    /**
     * If true, client will not enforce any server authentication (peer certificate chain
     * verification), "trust all certificates" TrustManager will be used. Convenient for local
     * development
     */
    public static boolean trustAllCertificates()
    {
        return Boolean.getBoolean("abiquo.rabbitmq.tls.trustallcertificates");
    }

    public static String sslProtocol()
    {
        return getProperty("abiquo.rabbitmq.tls.sslprotocol", "TLSv1.2");
    }

    public static final String TrustStoreKey = "abiquo.rabbitmq.tls.truststore";

    public static String trustStore()
    {
        return getProperty(TrustStoreKey, null);
    }

    public static final String TrustStorePassphraseKey =
        "abiquo.rabbitmq.tls.truststore.passphrase";

    public static String trustStorePassphrase()
    {
        return getProperty(TrustStorePassphraseKey, null);
    }

    public static String trustStoreType()
    {
        return getProperty("abiquo.rabbitmq.tls.truststore.type", "JKS");
    }

    public static String trustManagerAlgorithm()
    {
        return getProperty("abiquo.rabbitmq.tls.trustmanager.algorithm",
            KeyManagerFactory.getDefaultAlgorithm());
    }
}
