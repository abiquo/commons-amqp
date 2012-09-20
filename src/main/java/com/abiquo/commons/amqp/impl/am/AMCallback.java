/**
 * Copyright (C) 2008 - Abiquo Holdings S.L. All rights reserved.
 *
 * Please see /opt/abiquo/tomcat/webapps/legal/ on Abiquo server
 * or contact contact@abiquo.com for licensing information.
 */
package com.abiquo.commons.amqp.impl.am;

import com.abiquo.commons.amqp.impl.am.domain.TemplateStatusEvent;

public interface AMCallback
{
    public void onDownload(TemplateStatusEvent event);

    public void onNotDownload(TemplateStatusEvent event);

    public void onError(TemplateStatusEvent event);

    public void onDownloading(TemplateStatusEvent event);
}
