package com.affirm.loan.db;

import com.affirm.loan.model.Facility;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class AdditionalFacilityLoadTest {
    @Test
    public void additionalBankLoadTest(){
        BankStorage bankDBStorage = new BankStorage(AdditionalFacilityLoadTest
                .class.getResource("/successBanks.csv").getPath());

        FacilityStorage storage = new FacilityStorage(AdditionalFacilityLoadTest.
                class.getResource("/successFacilities.csv").getPath(),bankDBStorage);

        Assert.assertEquals(storage.getMap().size(),2);
        storage.process(Paths.get(AdditionalFacilityLoadTest.
                class.getResource("/additionalFacilities.csv").getPath()));

        Assert.assertEquals(storage.getMap().size(),4);
        Assert.assertEquals(storage.getSortedFacilityMap().first(), new Facility(3,1,0.06,2000000));

        Assert.assertEquals(storage.getGroupByBank().get(1).size(),3);
        Assert.assertEquals(storage.getGroupByBank().get(2).size(),1);
        Assert.assertEquals(storage.getGroupByBank().get(1).first(),new Facility(3,1,0.06,2000000));
    }
}
