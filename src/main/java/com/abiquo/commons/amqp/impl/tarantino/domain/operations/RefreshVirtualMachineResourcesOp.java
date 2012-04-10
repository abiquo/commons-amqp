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

package com.abiquo.commons.amqp.impl.tarantino.domain.operations;

/**
 * Refresh a virtual machine.
 * <p>
 * This operation is used to refresh the resources in the hypervisor when some operation that is not
 * directly related to the virtual machine is performed and makes changes to the resources attached
 * to it.
 * <p>
 * For example, when increasing the size of a iSCSI volume attached to a virtual machine deployed in
 * an ESX host, a rescan HBA should be performed in order to let the virtual machine see the new
 * size of the volume.
 */
public class RefreshVirtualMachineResourcesOp extends DatacenterJob
{

}
