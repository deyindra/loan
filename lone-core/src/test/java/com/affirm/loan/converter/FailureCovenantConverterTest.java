package com.affirm.loan.converter;

import com.affirm.loan.converter.exception.GenericConvertException;
import com.affirm.loan.db.BankStorage;
import com.affirm.loan.db.FacilityStorage;
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
public class FailureCovenantConverterTest extends AbstractGenericCSVConverterTest {
    private BankStorage bankStorage;
    private FacilityStorage facilityStorage;

    public FailureCovenantConverterTest(String input) {
        super(input);
        bankStorage = new BankStorage(FailureCovenantConverterTest.class.getResource("/successBanks.csv").getPath());
        facilityStorage = new FacilityStorage(FailureCovenantConverterTest
                .class.getResource("/successFacilities.csv").getPath(), bankStorage);

        facilityStorage.process(Paths.get(FailureCovenantConverterTest
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
                {"1"},
                {"ABC, 0.09, 1, CA"},
                {"1, ABC, 1, CA"},
                {"1, 0.09, ABC, CA"},
                {"1,,1"},
                {"1,1.2,1,"},
                {"1,-0.3,1,"},
                {"100,.2,1,"},
                {"3,.2,2,"},
                {"3,.2,100,CA"}
        });
    }

    @Test
    public void testBankConverter(){
        expectedException.expect(GenericConvertException.class);
        CovenantCSVConverter converter = new CovenantCSVConverter(input, facilityStorage,bankStorage);
        converter.convert();
    }

}
