package com.affirm.loan.db;

import com.affirm.loan.db.exception.DBStorageException;
import com.affirm.loan.rule.ExceptionLoggingRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SuccessCovenantDBStorageTest extends AbstractDBStorageTest{
    private BankStorage bankStorage;
    private FacilityStorage facilityStorage;

    public SuccessCovenantDBStorageTest(String file) {
        super(file);
        this.bankStorage = new BankStorage(SuccessCovenantDBStorageTest
                .class.getResource("/successBanks.csv").getPath());

        facilityStorage = new FacilityStorage(SuccessCovenantDBStorageTest
                .class.getResource("/successFacilities.csv").getPath(), bankStorage);

        facilityStorage.process(Paths.get(SuccessCovenantDBStorageTest
                .class.getResource("/additionalFacilities.csv").getPath()));
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {SuccessCovenantDBStorageTest.class.getResource("/successCovenant.csv").getPath()}
        });
    }

    @Test
    public void testSuccessBankDBStorage(){
        CovenantStorage storage =  new CovenantStorage(file, bankStorage,facilityStorage);
        Assert.assertTrue(!storage.getMap().isEmpty());
        Assert.assertTrue(!storage.getBannedStateMap().isEmpty());
        Assert.assertTrue(!storage.getMaxDefaultLikelihoodMap().isEmpty());
    }

}
