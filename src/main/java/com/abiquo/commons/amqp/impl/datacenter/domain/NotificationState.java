/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.datacenter.domain;

public enum NotificationState
{
    START, DONE, ERROR, ABORTED, ROLLBACK_START, ROLLBACK_DONE, ROLLBACK_ERROR, ROLLBACK_ABORTED
}
