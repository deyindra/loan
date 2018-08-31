package com.affirm.loan.reader;


import com.affirm.loan.db.BankStorage;
import com.affirm.loan.model.Bank;
import com.affirm.loan.model.Facility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

@RunWith(Parameterized.class)
public class SuccessFacilityStreamProcessorTest extends AbstractStreamProcessorTest {
    private ConcurrentMap<Integer, Facility> map;
    private BankStorage storage;

    public SuccessFacilityStreamProcessorTest(String filePath, boolean isParallel,
                                              ConcurrentMap<Integer, Facility> map) {
        super(filePath, isParallel);
        this.storage = new BankStorage(FailureFacilityStreamProcessorTest
                .class.getResource("/successBanks.csv").getPath());
        this.map = map;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"/successFacilities.csv", false,
                        getOutputMap(Arrays.asList(new Facility(1,2,0.06,126122), new Facility(2,1,0.07,61104)))},
                {"/successFacilities.csv", true,
                        getOutputMap(Arrays.asList(new Facility(1,2,0.06,126122), new Facility(2,1,0.07,61104)))}
        });
    }

    private static ConcurrentMap<Integer, Facility> getOutputMap(List<Facility> list){
        ConcurrentMap<Integer, Facility> resultMap = new ConcurrentHashMap<>();
        for(Facility b:list){
            resultMap.put(b.getFacilityId(),b);
        }
        return resultMap;
    }



    @Test
    public void testGroupByBankStreamProcessor() throws IOException {
        Path p = Paths.get(SuccessFacilityStreamProcessorTest.class.getResource(filePath).getPath());
        try(Stream<String> s= Files.lines(p).skip(1)){
            ConcurrentMap<Integer, Facility> resultMap = new ConcurrentHashMap<>();
            new FacilityStreamProcessor(storage).mapping(s, resultMap);
            Assert.assertEquals(resultMap,map);
        }
    }

}
