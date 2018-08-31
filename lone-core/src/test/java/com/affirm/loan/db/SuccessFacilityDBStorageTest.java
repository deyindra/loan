package com.affirm.loan.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SuccessFacilityDBStorageTest extends AbstractDBStorageTest{
    private BankStorage storage;
    public SuccessFacilityDBStorageTest(String file) {
        super(file);
        this.storage = new BankStorage(SuccessFacilityDBStorageTest
                .class.getResource("/successBanks.csv").getPath());
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {SuccessFacilityDBStorageTest.class.getResource("/successFacilities.csv").getPath()}
        });
    }

    @Test
    public void testSuccessBankDBStorage(){
        FacilityStorage facilityStorage = new FacilityStorage(file,storage);
        Assert.assertTrue(!facilityStorage.getMap().isEmpty());
    }

}
