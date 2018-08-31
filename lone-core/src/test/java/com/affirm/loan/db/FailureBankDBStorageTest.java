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
public class FailureBankDBStorageTest extends AbstractDBStorageTest{
    public FailureBankDBStorageTest(String file) {
        super(file);
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
                {FailureBankDBStorageTest.class.getResource("/failureBanks.csv").getPath()}
        });
    }

    @Test
    public void testSuccessBankDBStorage(){
        expectedException.expect(DBStorageException.class);
        new BankStorage(file);
    }

}
