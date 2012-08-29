/**
 * Abiquo premium edition
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

package com.abiquo.commons.amqp.impl.tarantino.domain.builder;

import static com.abiquo.commons.amqp.impl.tarantino.domain.builder.DiskSequenceToBusAndUnitNumber.numerateBusAndUnitBasedOnSequences;
import static com.abiquo.hypervisor.model.DiskDescription.DiskControllerType.IDE;
import static com.abiquo.hypervisor.model.DiskDescription.DiskControllerType.SCSI;
import static com.abiquo.testng.TestConfig.BASIC_UNIT_TESTS;

import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.abiquo.commons.amqp.impl.tarantino.domain.builder.DiskSequenceToBusAndUnitNumber.DiskAddress;
import com.abiquo.commons.amqp.impl.tarantino.domain.builder.DiskSequenceToBusAndUnitNumber.SequenceAndController;
import com.abiquo.commons.amqp.impl.tarantino.domain.exception.BuilderException;
import com.abiquo.hypervisor.model.DiskDescription;
import com.abiquo.hypervisor.model.DiskDescription.DiskControllerType;
import com.abiquo.hypervisor.model.DiskDescription.DiskFormatType;
import com.abiquo.hypervisor.model.VirtualMachineDefinition;

/**
 * Checks had disk and volumes sequence to unitNumber function
 */
@Test(groups = {BASIC_UNIT_TESTS})
public class DiskSequenceToBusAndUnitNumberTest
{
    final static Random rnd = new Random(System.currentTimeMillis());

    private VirtualMachineDefinition def;

    int seq = rnd.nextInt(1000);

    @Test(expectedExceptions = {BuilderException.class})
    public void test_sequenceRepetition()
    {
        def = vmdefinition(IDE, disk(seq, IDE), disk(seq, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);
        Assert.assertFalse(true, "sequence repetition");
    }

    @Test
    public void test_primaryIDE_secondaryIDE()
    {
        def = vmdefinition(IDE, disk(seq, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(seq, "0:1"));
    }

    @Test
    public void test_primaryIDE_secondarySCSI()
    {
        def = vmdefinition(IDE, disk(seq, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(seq, "0:0"));
    }

    @Test
    public void test_primarySCSI_secondaryIDE()
    {
        def = vmdefinition(SCSI, disk(seq, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(seq, "0:0"));
    }

    @Test
    public void test_primarySCSI_secondarySCSI()
    {
        def = vmdefinition(SCSI, disk(seq, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(seq, "0:1"));
    }

    @Test(expectedExceptions = {BuilderException.class}, enabled = false)
    // FIXME
    public void test_primaryIDE_toomany_IDE()
    {
        def = vmdefinition(IDE, //
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE),//
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE));
        def = numerateBusAndUnitBasedOnSequences(def);
        Assert.assertFalse(true, "too many ides");
    }

    @Test(expectedExceptions = {BuilderException.class}, enabled = false)
    // FIXME
    public void test_primarySCSI_toomany_IDE()
    {
        def = vmdefinition(SCSI, //
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE),//
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        def = vmdefinition(SCSI, //
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE),//
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE),//
            disk(rnd.nextInt(1000), IDE));

        def = numerateBusAndUnitBasedOnSequences(def);
        Assert.assertFalse(true, "too many ides");
    }

    @Test
    public void test_primaryIDE_secodariesIDE() throws Exception
    {
        def = vmdefinition(IDE, //
            disk(1, IDE), disk(2, IDE), disk(3, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(1, "0:1"), address(2, "1:0"), address(3, "1:1"));

        def = vmdefinition(IDE, //
            disk(1, IDE), disk(3, IDE), disk(2, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(1, "0:1"), address(2, "1:0"), address(3, "1:1"));

        def = vmdefinition(IDE, //
            disk(5, IDE), disk(7, IDE), disk(11, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(5, "0:1"), address(7, "1:0"), address(11, "1:1"));

        def = vmdefinition(IDE, //
            disk(5, IDE), disk(11, IDE), disk(7, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(5, "0:1"), address(7, "1:0"), address(11, "1:1"));
    }

    @Test
    public void test_primarySCSI_secodariesIDE() throws Exception
    {
        def = vmdefinition(SCSI, //
            disk(1, IDE), disk(2, IDE), disk(3, IDE), disk(4, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(1, "0:0"), address(2, "0:1"), address(3, "1:0"),
            address(4, "1:1"));

        def = vmdefinition(SCSI, //
            disk(5, IDE), disk(7, IDE), disk(11, IDE), disk(13, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(5, "0:0"), address(7, "0:1"), address(11, "1:0"),
            address(13, "1:1"));
    }

    @Test
    public void test_primaryIDE_secondariesIDEandSCSI()
    {
        def = vmdefinition(IDE, //
            disk(1, IDE), disk(2, IDE), disk(3, IDE), //
            disk(10, SCSI), disk(20, SCSI), disk(30, SCSI), disk(40, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(1, "0:1"), address(2, "1:0"), address(3, "1:1"), //
            address(10, "0:0"), address(20, "0:1"), address(30, "0:2"), address(40, "0:3"));

        def = vmdefinition(IDE, //
            disk(1, IDE), disk(3, IDE), disk(5, IDE), //
            disk(2, SCSI), disk(4, SCSI), disk(6, SCSI), disk(7, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(1, "0:1"), address(3, "1:0"), address(5, "1:1"), //
            address(2, "0:0"), address(4, "0:1"), address(6, "0:2"), address(7, "0:3"));
    }

    @Test
    public void test_primarySCSI_secondariesIDEandSCSI()
    {
        def = vmdefinition(SCSI, //
            disk(1, IDE), disk(2, IDE), disk(3, IDE),//
            disk(10, SCSI), disk(20, SCSI), disk(30, SCSI), disk(40, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(1, "0:0"), address(2, "0:1"), address(3, "1:0"), //
            address(10, "0:1"), address(20, "0:2"), address(30, "0:3"), address(40, "0:4"));

        def = vmdefinition(SCSI, //
            disk(1, IDE), disk(3, IDE), disk(5, IDE),//
            disk(2, SCSI), disk(4, SCSI), disk(6, SCSI), disk(7, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, address(1, "0:0"), address(3, "0:1"), address(5, "1:0"), //
            address(2, "0:1"), address(4, "0:2"), address(6, "0:3"), address(7, "0:4"));
    }

    /**
     * NOW WITH CD-ROM SUPERPOWERS
     */

    @Test
    public void test_primaryIDE_secondaryIDE_andCdrom()
    {
        def = vmdefinition(IDE, true, disk(seq, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:1", address(seq, "1:0"));
    }

    @Test
    public void test_primaryIDE_secondarySCSI_andCdrom()
    {
        def = vmdefinition(IDE, true, disk(seq, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:1", address(seq, "0:0"));
    }

    @Test
    public void test_primarySCSI_secondaryIDE_andCdrom()
    {
        def = vmdefinition(SCSI, true, disk(seq, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:0", address(seq, "0:1"));
    }

    @Test
    public void test_primarySCSI_secondarySCSI_andCdrom()
    {
        def = vmdefinition(SCSI, true, disk(seq, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:0", address(seq, "0:1"));
    }

    @Test(enabled = false)
    // FIXME
    public void test_primaryIDE_toomany_IDE_andCdrom()
    {
        def = vmdefinition(IDE, true,//
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE),//
            disk(rnd.nextInt(1000), IDE));
        try
        {
            def = numerateBusAndUnitBasedOnSequences(def);
            Assert.assertFalse(true, "too many ides");
        }
        catch (BuilderException e)
        {
        }
    }

    @Test(enabled = false)
    // FIXME
    public void test_primarySCSI_toomany_IDE_andCdrom()
    {
        def = vmdefinition(SCSI, true,//
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE),//
            disk(rnd.nextInt(1000), IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        def = vmdefinition(SCSI, true,//
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE),//
            disk(rnd.nextInt(1000), IDE), disk(rnd.nextInt(1000), IDE));
        try
        {
            def = numerateBusAndUnitBasedOnSequences(def);
            Assert.assertFalse(true, "too many ides");
        }
        catch (BuilderException e)
        {
        }
    }

    @Test
    public void test_primaryIDE_secodariesIDE_andCdrom() throws Exception
    {
        def = vmdefinition(IDE, true, //
            disk(1, IDE), disk(2, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:1", address(1, "1:0"), address(2, "1:1"));

        def = vmdefinition(IDE, true,//
            disk(3, IDE), disk(2, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:1", address(2, "1:0"), address(3, "1:1"));

        def = vmdefinition(IDE, true,//
            disk(7, IDE), disk(11, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:1", address(7, "1:0"), address(11, "1:1"));

        def = vmdefinition(IDE, true,//
            disk(11, IDE), disk(7, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:1", address(7, "1:0"), address(11, "1:1"));
    }

    @Test
    public void test_primarySCSI_secodariesIDE_andCdrom() throws Exception
    {
        def = vmdefinition(SCSI, true,//
            disk(2, IDE), disk(3, IDE), disk(4, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:0", address(2, "0:1"), address(3, "1:0"), address(4, "1:1"));

        def = vmdefinition(SCSI, true,//
            disk(7, IDE), disk(11, IDE), disk(13, IDE));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:0", address(7, "0:1"), address(11, "1:0"), address(13, "1:1"));
    }

    @Test
    public void test_primaryIDE_secondariesIDEandSCSI_andCdrom()
    {
        def = vmdefinition(IDE, true,//
            disk(2, IDE), disk(3, IDE), //
            disk(10, SCSI), disk(20, SCSI), disk(30, SCSI), disk(40, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:1", address(2, "1:0"), address(3, "1:1"), //
            address(10, "0:0"), address(20, "0:1"), address(30, "0:2"), address(40, "0:3"));

        def = vmdefinition(IDE, true,//
            disk(3, IDE), disk(5, IDE), //
            disk(2, SCSI), disk(4, SCSI), disk(6, SCSI), disk(7, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:1", address(3, "1:0"), address(5, "1:1"), //
            address(2, "0:0"), address(4, "0:1"), address(6, "0:2"), address(7, "0:3"));
    }

    @Test
    public void test_primarySCSI_secondariesIDEandSCSI_andCdrom()
    {
        def = vmdefinition(SCSI, true,//
            disk(2, IDE), disk(3, IDE),//
            disk(10, SCSI), disk(20, SCSI), disk(30, SCSI), disk(40, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:0", address(2, "0:1"), address(3, "1:0"), //
            address(10, "0:1"), address(20, "0:2"), address(30, "0:3"), address(40, "0:4"));

        def = vmdefinition(SCSI, true,//
            disk(3, IDE), disk(5, IDE),//
            disk(2, SCSI), disk(4, SCSI), disk(6, SCSI), disk(7, SCSI));

        def = numerateBusAndUnitBasedOnSequences(def);

        checkvmdefinition(def, "0:0", address(3, "0:1"), address(5, "1:0"), //
            address(2, "0:1"), address(4, "0:2"), address(6, "0:3"), address(7, "0:4"));
    }

    /**
     * AUX METHODS
     */

    public static void checkvmdefinition(final VirtualMachineDefinition vmdefinition,
        final DiskAddress... addresses)
    {
        for (DiskAddress address : addresses)
        {
            DiskDescription disk = vmdefinition.getSecondaryDiskBySequence(address.sequence);
            Assert.assertNotNull(disk, "get by sequence not found" + address.sequence);

            Assert.assertEquals(String.format("%d:%d", disk.getBusNumber(), disk.getUnitNumber()),
                String.format("%d:%d", address.busNumber, address.unitNumber));

        }
    }

    public static void checkvmdefinition(final VirtualMachineDefinition vmdefinition,
        final String cdromAddress, final DiskAddress... addresses)
    {
        checkvmdefinition(vmdefinition, addresses);
        Assert.assertEquals(String.format("%d:%d", vmdefinition.getCdrom().getBusNumber(),
            vmdefinition.getCdrom().getUnitNumber()), cdromAddress);
    }

    public static VirtualMachineDefinition vmdefinition(
        final DiskControllerType primaryDiskController,
        final SequenceAndControllerAndManId... disks)
    {
        VirtualMachineDescriptionBuilder builder =
            new VirtualMachineDescriptionBuilder().hardware(2, 2048)
                .setBasics("uuid", "name")
                .//
                primaryDisk(DiskFormatType.RAW, 2024, "repository", "sourcePath",
                    "destinationDatastore", "repositoryManagerAddress", primaryDiskController);

        for (SequenceAndControllerAndManId disk : disks)
        {
            builder.addSecondaryHardDisk(2048, disk.sequence,
                String.format("disk %d %s", disk.sequence, disk.controller), disk.controller,
                disk.manId);
        }

        return builder.build();
    }

    public static VirtualMachineDefinition vmdefinition(
        final DiskControllerType primaryDiskController, final boolean cdrom,
        final SequenceAndControllerAndManId... disks)
    {
        VirtualMachineDefinition def = vmdefinition(primaryDiskController, disks);
        if (cdrom)
        {
            def.setCdrom(new VirtualMachineDefinition.Cdrom());
        }

        return def;
    }

    static public class SequenceAndControllerAndManId extends SequenceAndController
    {
        public Integer manId;

        public SequenceAndControllerAndManId(final Integer sequence,
            final DiskControllerType controller, final Integer manId)
        {
            super(sequence, controller);
            this.manId = manId;
        }
    }

    public static SequenceAndControllerAndManId disk(final Integer manId, final Integer sequence,
        final DiskControllerType controller)
    {
        return new SequenceAndControllerAndManId(sequence, controller, manId);
    }

    public static SequenceAndControllerAndManId disk(final Integer sequence,
        final DiskControllerType controller)
    {
        return new SequenceAndControllerAndManId(sequence, controller, sequence);
    }

    public static DiskAddress address(final Integer sequence, final Integer busNumber,
        final Integer unitNumber)
    {
        return new DiskAddress(sequence, busNumber, unitNumber);
    }

    public static DiskAddress address(final Integer sequence, final String address)
    {
        return new DiskAddress(sequence,
            Integer.valueOf(address.split(":")[0]),
            Integer.valueOf(address.split(":")[1]));
    }

}
