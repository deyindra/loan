package com.affirm.loan.converter;

import com.affirm.loan.converter.exception.GenericConvertException;
import com.affirm.loan.rule.ExceptionLoggingRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class FailureLoanCSVConverterTest extends AbstractGenericCSVConverterTest {

    public FailureLoanCSVConverterTest(String input) {
        super(input);
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
                {"ABC, 1, 1.0, 1.0, CA"},
                {"1, ABC, 1.0, 1.0, CA"},
                {"1, 1, ABC, 1.0, CA"},
                {"1, 1, 1.0, ABC, CA"},
                {"1, -1, 1.0, 1.0, CA"},
                {"1, 1, -0.2, 1.0, CA"},
                {"1, 1, 1.2, 1.0, CA"},
                {"1, 1, 0.2, -1.0, CA"},
                {"1, 1, 0.2, 1.9, CA"},
                {"1, 1, 0.2, 1.9,"},
        });
    }

    @Test
    public void testBankConverter(){
        expectedException.expect(GenericConvertException.class);
        LoanCSVConverter converter = new LoanCSVConverter(input);
        converter.convert();
    }

}
