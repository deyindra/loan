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
public class FailureBankCSVConverterTest extends AbstractGenericCSVConverterTest {

    public FailureBankCSVConverterTest(String input) {
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
                {"ABC, ANG"}
        });
    }

    @Test
    public void testBankConverter(){
        expectedException.expect(GenericConvertException.class);
        BankCSVConverter converter = new BankCSVConverter(input);
        converter.convert();
    }

}
