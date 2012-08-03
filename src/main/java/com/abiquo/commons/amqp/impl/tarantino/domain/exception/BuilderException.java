package com.abiquo.commons.amqp.impl.tarantino.domain.exception;

public class BuilderException extends RuntimeException
{
    private static final long serialVersionUID = -55721416646924469L;

    public enum VirtualMachineDescriptionBuilderError
    {
        NO_PRIMARY_DISK, NO_IDENTIFIED, NO_HARDWARE, IDE_FULL, SEQUENCE_REPETITION;
    }
    
    final VirtualMachineDescriptionBuilderError error;

    public BuilderException(VirtualMachineDescriptionBuilderError error)
    {
        super(error.name());
        this.error = error;
    }

    public VirtualMachineDescriptionBuilderError getError()
    {
        return error;
    }
}
