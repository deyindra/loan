package com.affirm.loan.reader;


import com.affirm.loan.model.Bank;
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
public class SuccessBankStreamProcessorTest extends AbstractStreamProcessorTest {
    private ConcurrentMap<Integer, Bank> map;

    public SuccessBankStreamProcessorTest(String filePath, boolean isParallel, ConcurrentMap<Integer, Bank> map) {
        super(filePath, isParallel);
        this.map = map;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"/successBanks.csv", false,
                        getOutputMap(Arrays.asList(new Bank(1, "Chase"), new Bank(2,"Bank of America")))},
                {"/successBanks.csv", true,
                        getOutputMap(Arrays.asList(new Bank(1, "Chase"), new Bank(2,"Bank of America")))}
        });
    }

    private static ConcurrentMap<Integer, Bank> getOutputMap(List<Bank> list){
        ConcurrentMap<Integer, Bank> resultMap = new ConcurrentHashMap<>();
        for(Bank b:list){
            resultMap.put(b.getId(),b);
        }
        return resultMap;
    }



    @Test
    public void testGroupByBankStreamProcessor() throws IOException {
        Path p = Paths.get(SuccessBankStreamProcessorTest.class.getResource(filePath).getPath());
        try(Stream<String> s= Files.lines(p).skip(1)){
            ConcurrentMap<Integer, Bank> resultMap = new ConcurrentHashMap<>();
            new BankStreamProcessor().mapping(s, resultMap);
            Assert.assertEquals(resultMap,map);
        }
    }

}
