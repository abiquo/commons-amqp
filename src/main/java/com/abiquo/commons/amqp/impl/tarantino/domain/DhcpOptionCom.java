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

package com.abiquo.commons.amqp.impl.tarantino.domain;

public class DhcpOptionCom
{

    protected Integer option;

    protected String gateway;

    protected String networkAddress;

    protected Integer mask;

    protected String netmask;

    // public DhcpOptionCom(final Integer mask, final Integer option, final String gateway,
    // final String networkAddress, final String netmask)
    // {
    // this.option = option;
    // this.gateway = gateway;
    // this.networkAddress = networkAddress;
    // this.mask = mask;
    // this.netmask = netmask;
    // }

    public Integer getOption()
    {
        return option;
    }

    public void setOption(final Integer option)
    {
        this.option = option;
    }

    public String getGateway()
    {
        return gateway;
    }

    public void setGateway(final String gateway)
    {
        this.gateway = gateway;
    }

    public String getNetworkAddress()
    {
        return networkAddress;
    }

    public void setNetworkAddress(final String networkAddress)
    {
        this.networkAddress = networkAddress;
    }

    public Integer getMask()
    {
        return mask;
    }

    public void setMask(final Integer mask)
    {
        this.mask = mask;
    }

    public String getNetmask()
    {
        return netmask;
    }

    public void setNetmask(final String netmask)
    {
        this.netmask = netmask;
    }

}
