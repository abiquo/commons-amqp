package com.abiquo.commons.amqp.impl.tarantino.domain.builder;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abiquo.commons.amqp.impl.tarantino.domain.DiskDescription.DiskControllerType;
import com.abiquo.commons.amqp.impl.tarantino.domain.SecondaryDiskStandard;
import com.abiquo.commons.amqp.impl.tarantino.domain.SecondaryDiskStateful;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition;
import com.abiquo.commons.amqp.impl.tarantino.domain.VirtualMachineDefinition.SecondaryDisks;
import com.abiquo.commons.amqp.impl.tarantino.domain.exception.BuilderException;
import com.abiquo.commons.amqp.impl.tarantino.domain.exception.BuilderException.VirtualMachineDescriptionBuilderError;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;

public class DiskSequenceToBusAndUnitNumber
{
    private final static Logger LOG = LoggerFactory.getLogger(DiskSequenceToBusAndUnitNumber.class);
    
    /**
     * Set the bus and unit number for all the Disks in the virtual machine definition. Including
     * PrimaryDisk, SecondaryStandardDisk, SecondaryStatefulDisk and CDRom.
     * <ul>
     * <li>Primary disk always sequence = 0 => (0:0) (other secondaries disk also uses 0)</li>
     * <li>CD-ROM always IDE and with higher priority of other secondary disks</li>
     * <li>Sequence numbers no need to be consecutive. Then need all the sequences to select the
     * unit numbers, by ordering the sequence numbers in groups of controller types (one ordered
     * list for IDE and other for SCSI), then enumerate following the controller type rules (SCSI :
     * 0:x and IDE only 2 bus with 2 units each)</li>
     * <li>HD and volumes CAN NOT share sequences</li>
     * </ul>
     */
    public static VirtualMachineDefinition numerateBusAndUnitBasedOnSequences(
        VirtualMachineDefinition vmdef)
    {

        final List<SequenceAndController> sequences = getSequencesWithControllerType(vmdef);

        for (DiskAddress address : numerateBusAndUnitBasedOnSequence(sequences,
            DiskControllerType.IDE == vmdef.getPrimaryDisk().getDiskControllerType(), vmdef
                .isCdromSet()))
        {
            if (-1 == address.sequence) // has cdrom set
            {
                LOG.debug("CD-ROM will use {}:{}",address.busNumber, address.unitNumber);
                
                vmdef.getCdrom().setBusNumber(address.busNumber);
                vmdef.getCdrom().setUnitNumber(address.unitNumber);
            }
            else
            {
                LOG.debug("Secondary Disk with sequence {} will use {} ", address.sequence, String
                    .format("%d:%d", address.busNumber, address.unitNumber));
                
                vmdef.getSecondaryDiskBySequence(address.sequence).//
                    setBusAndUnitNumber(address.busNumber, address.unitNumber);
            }
        }

        if (vmdef.getPrimaryDisk().isStandard())
        {
            vmdef.getPrimaryDisk().getDiskStandard().setBusAndUnitNumber(0, 0);
        }
        else
        {
            vmdef.getPrimaryDisk().getDiskStateful().setBusAndUnitNumber(0, 0);
        }

        return vmdef;
    }

    private static List<SequenceAndController> getSequencesWithControllerType(
        VirtualMachineDefinition vmdef)
    {
        final List<SequenceAndController> sequences = new LinkedList<SequenceAndController>();

        final SecondaryDisks secondaries = vmdef.getSecondaryDisks();

        for (SecondaryDiskStandard standard : secondaries.getStandardDisks())
        {
            sequences.add(new SequenceAndController(standard.getSequence(), standard
                .getDiskControllerType()));
        }
        for (SecondaryDiskStateful stateful : secondaries.getStatefulDisks())
        {
            sequences.add(new SequenceAndController(stateful.getSequence(), stateful
                .getDiskControllerType()));
        }

        checkNoSequenceRepetition(sequences);

        return sequences;
    }

    private static List<DiskAddress> numerateBusAndUnitBasedOnSequence(
        List<SequenceAndController> sequences, boolean primaryIde, boolean isCdrom)
    {
        if (isCdrom)
        {
            // setting CD-ROM with sequence -1
            // used by primary disk, convention done in #numerateBusAndUnitBasedOnSequences)
            sequences.add(new SequenceAndController(-1, DiskControllerType.IDE));
        }

        ImmutableList<SequenceAndController> sortedIdes =
            ImmutableList.copyOf(Ordering.natural().onResultOf(sequenceOrdering).sortedCopy(
                Iterables.filter(sequences, new Predicate<SequenceAndController>()
                {
                    @Override
                    public boolean apply(SequenceAndController input)
                    {
                        return DiskControllerType.IDE == input.controller;
                    }
                })));

        ImmutableList<SequenceAndController> sortedScsi =
            ImmutableList.copyOf(Ordering.natural().onResultOf(sequenceOrdering).sortedCopy(
                Iterables.filter(sequences, new Predicate<SequenceAndController>()
                {
                    @Override
                    public boolean apply(SequenceAndController input)
                    {
                        return DiskControllerType.SCSI == input.controller;
                    }
                })));

        List<DiskAddress> addresses = new LinkedList<DiskAddress>();

        Integer regularSequence = primaryIde ? 1 : 0;
        for (SequenceAndController seq : sortedIdes)
        {
            // FIXME now is controlled in tarantino in order to avoid a transaction mess in TarantinoService
            // if (regularSequence > 3)
            // {
            //     throw new BuilderException(VirtualMachineDescriptionBuilderError.IDE_FULL);
            // }

            addresses.add(new DiskAddress(seq.sequence, //
                (int) (regularSequence / 2), regularSequence % 2));
            regularSequence++;
        }

        //

        regularSequence = primaryIde ? 0 : 1;
        for (SequenceAndController seq : sortedScsi)
        {
            addresses.add(new DiskAddress(seq.sequence, 0, regularSequence));
            regularSequence++;
        }

        return addresses;
    }

    private static final Function<SequenceAndController, Integer> sequenceOrdering =
        new Function<SequenceAndController, Integer>()
        {
            @Override
            public Integer apply(SequenceAndController from)
            {
                return from.sequence;
            }
        };

    private static void checkNoSequenceRepetition(List<SequenceAndController> sequences)
    {
        HashSet<Integer> setseq = new HashSet<Integer>();
        for (SequenceAndController s : sequences)
        {
            if (!setseq.add(s.sequence))
            {
                throw new BuilderException(
                    VirtualMachineDescriptionBuilderError.SEQUENCE_REPETITION);
            }
        }
    }

    /**
     * Holder classes
     */

    static class SequenceAndController
    {
        public final Integer sequence;

        public final DiskControllerType controller;

        public SequenceAndController(Integer sequence, DiskControllerType controller)
        {
            super();
            this.sequence = sequence;
            this.controller = controller;
        }
    }

    static public class DiskAddress
    {
        public final Integer sequence;

        public final Integer busNumber;

        public final Integer unitNumber;
        
        public DiskAddress(Integer sequence, Integer busNumber, Integer unitNumber)
        {
            super();
            this.sequence = sequence;
            this.busNumber = busNumber;
            this.unitNumber = unitNumber;
        }
    }
}
