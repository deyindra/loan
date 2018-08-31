package com.affirm.loan.db;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SuccessBankDBStorageTest extends AbstractDBStorageTest{
    public SuccessBankDBStorageTest(String file) {
        super(file);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {SuccessBankDBStorageTest.class.getResource("/successBanks.csv").getPath()}
        });
    }

    @Test
    public void testSuccessBankDBStorage(){
        BankStorage bankStorage = new BankStorage(file);
        Assert.assertTrue(!bankStorage.getMap().isEmpty());
    }

}
