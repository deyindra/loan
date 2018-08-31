package com.affirm.loan.db;

import com.affirm.loan.db.exception.DBStorageException;
import com.affirm.loan.rule.ExceptionLoggingRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FailureCovenantDBStorageTest extends AbstractDBStorageTest{
    private BankStorage bankStorage;
    private FacilityStorage facilityStorage;

    public FailureCovenantDBStorageTest(String file) {
        super(file);
        this.bankStorage = new BankStorage(FailureCovenantDBStorageTest
                .class.getResource("/successBanks.csv").getPath());

        facilityStorage = new FacilityStorage(FailureCovenantDBStorageTest
                .class.getResource("/successFacilities.csv").getPath(), bankStorage);

        facilityStorage.process(Paths.get(FailureCovenantDBStorageTest
                .class.getResource("/additionalFacilities.csv").getPath()));
    }


    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {null},
                {" "},
                {"/abc"},
                {Paths.get(System.getProperty("java.io.tmpdir")).toString()},
                {FailureCovenantDBStorageTest.class.getResource("/failureCovenant.csv").getPath()}
        });
    }

    @Test
    public void testSuccessBankDBStorage(){
        expectedException.expect(DBStorageException.class);
        new CovenantStorage(file, bankStorage,facilityStorage);
    }

}
