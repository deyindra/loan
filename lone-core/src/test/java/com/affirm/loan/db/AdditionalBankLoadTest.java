package com.affirm.loan.db;

import com.affirm.loan.model.Bank;
import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class AdditionalBankLoadTest {
    @Test
    public void additionalBankLoadTest(){
        DBStorage<Integer, Bank> storage = new BankStorage(AdditionalBankLoadTest.
                class.getResource("/successBanks.csv").getPath());

        Assert.assertEquals(storage.map.size(),2);
        storage.process(Paths.get(AdditionalBankLoadTest.
                class.getResource("/additionalBanks.csv").getPath()));

        Assert.assertEquals(storage.getMap().size(),4);
    }
}
