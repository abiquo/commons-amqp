/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.tarantino.domain;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeInfo.As;
import org.codehaus.jackson.annotate.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "@class")
public class HypervisorConnection
{
    protected HypervisorType hypervisorType;

    protected String ip;

    protected String loginUser;

    protected String loginPassword;

    public String getIp()
    {
        return ip;
    }

    public void setIp(final String value)
    {
        this.ip = value;
    }

    public String getLoginUser()
    {
        return loginUser;
    }

    public void setLoginUser(final String value)
    {
        this.loginUser = value;
    }

    public String getLoginPassword()
    {
        return loginPassword;
    }

    public void setLoginPassword(final String value)
    {
        this.loginPassword = value;
    }

    public HypervisorType getHypervisorType()
    {
        return hypervisorType;
    }

    public void setHypervisorType(final HypervisorType hypervisorType)
    {
        this.hypervisorType = hypervisorType;
    }

    @JsonIgnore
    public String getConnectionURI()
    {
        return this.hypervisorType.getConnectionURI(getIp());
    }

    // TODO duplicated
    public enum HypervisorType
    {
        /** Virtual Box */
        VBOX(18083),

        /** KVM */
        KVM(8889),

        /** XEN */
        XEN_3(8889),

        /** ESXi */
        VMX_04(443),

        /** Hyper V */
        HYPERV_301(5985),

        /** Xen Server */
        XENSERVER(9363),

        /** Test actor */
        TEST(56);

        public final int defaultPort;

        private final int defaultAimPort = 8889;

        public int getAimPort()
        {
            // TODO only if(this == VBOX || this == KVM || this == XEN_3)
            return defaultAimPort;
        }

        private HypervisorType(final int port)
        {
            this.defaultPort = port;
        }

        public String getConnectionURI(final String ip)
        {
            switch (this)
            {
                case VMX_04:
                    return String.format("https://%s:%d/sdk", ip, defaultPort);

                case KVM:
                    return String.format("qemu+tcp://%s/system?no_tty=1", ip);

                case XEN_3:
                    return String.format("xen+tcp://%s/?no_tty=1", ip);

                case VBOX:
                    return String.format("http://%s:%d", ip, defaultPort);

                case XENSERVER:
                    return String.format("http://%s", ip);

                case HYPERV_301:
                    return String.format("http://%s:%d", ip, defaultPort);

                case TEST:
                    return ip;

                default:
                    throw new RuntimeException("Unable to build the connection URI, unknown hypervisor.");
            }
        }
    }
}
