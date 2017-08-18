package com.abiquo.commons.amqp.exception;

public class SSLException extends Exception
{
    private static final long serialVersionUID = 2700577308583950195L;

    public SSLException(final Exception exception)
    {
        super(exception);
    }
}
