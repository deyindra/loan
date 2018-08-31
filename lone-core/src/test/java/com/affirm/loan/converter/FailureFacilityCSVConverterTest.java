package com.affirm.loan.converter;

import com.affirm.loan.converter.exception.GenericConvertException;
import com.affirm.loan.db.BankStorage;
import com.affirm.loan.rule.ExceptionLoggingRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FailureFacilityCSVConverterTest extends AbstractGenericCSVConverterTest {
    private BankStorage storage;

    public FailureFacilityCSVConverterTest(String input) {
        super(input);
        storage = new BankStorage(FailureFacilityCSVConverterTest.class.getResource("/successBanks.csv").getPath());
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
                {"ABC, 0.9, 1, 1"},
                {"1, ABC, 1, 1"},
                {"1, 0.9, ABC, 1"},
                {"1, 0.9, 1, ABC"},
                {"0, 0.9, 1, 1"},
                {"-10, 0.9, 1, 1"},
                {"10, -0.9, 1, 1"},
                {"10, 1.9, 1, 1"},
                {"10, 1.9, 1, 5"}
        });
    }

    @Test
    public void testBankConverter(){
        expectedException.expect(GenericConvertException.class);
        FacilityCSVConverter converter = new FacilityCSVConverter(input,storage);
        converter.convert();
    }

}
