/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
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
