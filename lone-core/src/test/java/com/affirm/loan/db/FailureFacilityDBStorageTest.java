package com.affirm.loan.db;

import com.affirm.loan.db.exception.DBStorageException;
import com.affirm.loan.reader.FailureFacilityStreamProcessorTest;
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
public class FailureFacilityDBStorageTest extends AbstractDBStorageTest{
    private BankStorage storage;


    public FailureFacilityDBStorageTest(String file) {
        super(file);
        this.storage = new BankStorage(FailureFacilityDBStorageTest
                .class.getResource("/successBanks.csv").getPath());
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
                {FailureFacilityDBStorageTest.class.getResource("/failureFacilities.csv").getPath()}
        });
    }

    @Test
    public void testSuccessBankDBStorage(){
        expectedException.expect(DBStorageException.class);
        new FacilityStorage(file,storage);
    }

}
