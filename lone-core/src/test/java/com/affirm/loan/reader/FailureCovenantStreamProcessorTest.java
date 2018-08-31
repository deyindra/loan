package com.affirm.loan.reader;


import com.affirm.loan.db.BankStorage;
import com.affirm.loan.db.FacilityStorage;
import com.affirm.loan.reader.exception.StreamProcessorException;
import com.affirm.loan.rule.ExceptionLoggingRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class FailureCovenantStreamProcessorTest extends AbstractStreamProcessorTest {
    private BankStorage bankStorage;
    private FacilityStorage facilityStorage;
    public FailureCovenantStreamProcessorTest(String filePath, boolean isParallel) {
        super(filePath, isParallel);

        this.bankStorage = new BankStorage(FailureCovenantStreamProcessorTest
                .class.getResource("/successBanks.csv").getPath());
        facilityStorage = new FacilityStorage(FailureCovenantStreamProcessorTest
                .class.getResource("/successFacilities.csv").getPath(), bankStorage);

        facilityStorage.process(Paths.get(FailureCovenantStreamProcessorTest
                .class.getResource("/additionalFacilities.csv").getPath()));

    }

    @Rule
    public ExceptionLoggingRule exceptionLoggingRule = new ExceptionLoggingRule();
    @Rule public ExpectedException expectedException = ExpectedException.none();


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"/failureCovenant.csv", false},
                {"/failureCovenant.csv", true}
        });
    }

    @Test
    public void testFailureGroupByBankStreamProcessor() throws IOException {
        expectedException.expect(StreamProcessorException.class);
        Path p = Paths.get(FailureCovenantStreamProcessorTest.class.getResource(filePath).getPath());
        try(Stream<String> s= isParallel ? Files.lines(p).parallel().skip(1) : Files.lines(p).skip(1)){
            new CovenantStreamProcessor(bankStorage,facilityStorage).mapping(s, new ConcurrentHashMap<>());
        }
    }

}
