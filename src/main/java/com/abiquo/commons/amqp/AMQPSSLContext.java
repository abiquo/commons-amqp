/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp;

import static com.abiquo.commons.amqp.AMQPProperties.TrustStoreKey;
import static com.abiquo.commons.amqp.AMQPProperties.TrustStorePassphraseKey;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Optional;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import com.abiquo.commons.amqp.exception.SSLException;
import com.rabbitmq.client.TrustEverythingTrustManager;

public class AMQPSSLContext
{
    public static Optional<SSLContext> buildSSLContext() throws SSLException
    {
        if (!AMQPProperties.isTLSEnabled())
        {
            return Optional.empty();
        }

        try
        {
            final SSLContext sslContext = SSLContext.getInstance(AMQPProperties.sslProtocol());

            if (AMQPProperties.trustAllCertificates())
            {
                sslContext.init(null, new TrustManager[] {new TrustEverythingTrustManager()}, null);
            }
            else
            {
                checkArgument(!isNullOrEmpty(AMQPProperties.trustStore()), //
                    TrustStoreKey + " is null or empty");
                checkArgument(!isNullOrEmpty(AMQPProperties.trustStorePassphrase()), //
                    TrustStorePassphraseKey + " is null or empty");

                final String trustStorePath = AMQPProperties.trustStore();
                final char[] trustPassphrase = AMQPProperties.trustStorePassphrase().toCharArray();
                final String keyStoreType = AMQPProperties.trustStoreType();
                final String trustManagerAlgorithm = AMQPProperties.trustManagerAlgorithm();

                final KeyStore keyStore = KeyStore.getInstance(keyStoreType);
                keyStore.load(new FileInputStream(trustStorePath), trustPassphrase);

                final TrustManagerFactory tmf =
                    TrustManagerFactory.getInstance(trustManagerAlgorithm);
                tmf.init(keyStore);

                sslContext.init(null, tmf.getTrustManagers(), null);
            }

            return Optional.of(sslContext);
        }
        catch (Exception e)
        {
            throw new SSLException(e);
        }
    }
}
