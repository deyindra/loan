package com.affirm.loan.converter;

import com.affirm.loan.db.BankStorage;
import com.affirm.loan.db.FacilityStorage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SuccessCovenantConverterTest extends AbstractGenericCSVConverterTest {
    private BankStorage bankStorage;
    private FacilityStorage facilityStorage;
    private int expectedSize;

    public SuccessCovenantConverterTest(String input, int expectedSize) {
        super(input);
        bankStorage = new BankStorage(SuccessCovenantConverterTest.class.getResource("/successBanks.csv").getPath());
        facilityStorage = new FacilityStorage(SuccessCovenantConverterTest
                .class.getResource("/successFacilities.csv").getPath(), bankStorage);

        facilityStorage.process(Paths.get(SuccessCovenantConverterTest
                .class.getResource("/additionalFacilities.csv").getPath()));

        this.expectedSize = expectedSize;
    }



    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {",.2,1,CA",6},
                {",.2,1,",3},
                {",,1,CA",3},
                {"3,,1,CA",1},
                {"3,.2,1,",1},
                {"3,.2,1,CA",2},
        });
    }

    @Test
    public void testBankConverter(){
        CovenantCSVConverter converter = new CovenantCSVConverter(input, facilityStorage,bankStorage);
        Assert.assertEquals(converter.convert().size(),expectedSize);
    }

}
