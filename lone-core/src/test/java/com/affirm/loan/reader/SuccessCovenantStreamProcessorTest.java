package com.affirm.loan.reader;


import com.affirm.loan.db.BankStorage;
import com.affirm.loan.db.FacilityStorage;
import com.affirm.loan.model.Covenant;
import com.affirm.loan.reader.exception.StreamProcessorException;
import com.affirm.loan.rule.ExceptionLoggingRule;
import org.junit.Assert;
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
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class SuccessCovenantStreamProcessorTest extends AbstractStreamProcessorTest {
    private BankStorage bankStorage;
    private FacilityStorage facilityStorage;
    private int size;
    public SuccessCovenantStreamProcessorTest(String filePath, boolean isParallel, int size) {
        super(filePath, isParallel);

        this.bankStorage = new BankStorage(SuccessCovenantStreamProcessorTest
                .class.getResource("/successBanks.csv").getPath());
        facilityStorage = new FacilityStorage(SuccessCovenantStreamProcessorTest
                .class.getResource("/successFacilities.csv").getPath(), bankStorage);

        facilityStorage.process(Paths.get(SuccessCovenantStreamProcessorTest
                .class.getResource("/additionalFacilities.csv").getPath()));
        this.size = size;
    }



    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"/successCovenant.csv", false,11},
                {"/successCovenant.csv", true,11}
        });
    }

    @Test
    public void testFailureGroupByBankStreamProcessor() throws IOException {
        Path p = Paths.get(SuccessCovenantStreamProcessorTest.class.getResource(filePath).getPath());
        try(Stream<String> s= isParallel ? Files.lines(p).parallel().skip(1) : Files.lines(p).skip(1)){
            ConcurrentMap<CovenantStreamProcessor.CovenantPK, Covenant> map = new ConcurrentHashMap<>();
            new CovenantStreamProcessor(bankStorage,facilityStorage).mapping(s, map);
            Assert.assertEquals(map.size(),size);
        }
    }

}
