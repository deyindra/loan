package com.affirm.loan.converter;

import com.affirm.loan.db.BankStorage;
import com.affirm.loan.model.Facility;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@RunWith(Parameterized.class)
public class SuccessFacilityCSVConverterTest extends AbstractGenericCSVConverterTest {
    private BankStorage storage;
    private Collection<Facility> facilities;

    public SuccessFacilityCSVConverterTest(String input, Facility facility) {
        super(input);
        storage = new BankStorage(SuccessFacilityCSVConverterTest.class.getResource("/successBanks.csv").getPath());
        this.facilities = Collections.singleton(facility);
    }



    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"10, 0.9, 1, 1", new Facility(1,1,0.9, 10)},
                {"10, 0.7, 1, 2", new Facility(1,2,0.7, 10)}
        });
    }

    @Test
    public void testBankConverter(){
        FacilityCSVConverter converter = new FacilityCSVConverter(input,storage);
        Assert.assertEquals(converter.convert(),facilities);
    }

}
