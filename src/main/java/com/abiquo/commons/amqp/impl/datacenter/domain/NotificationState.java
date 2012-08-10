package com.abiquo.commons.amqp.impl.datacenter.domain;

public enum NotificationState
{
    START, DONE, ERROR, ABORTED, ROLLBACK_START, ROLLBACK_DONE, ROLLBACK_ERROR, ROLLBACK_ABORTED
}
