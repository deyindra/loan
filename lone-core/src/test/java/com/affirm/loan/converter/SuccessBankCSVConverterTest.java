package com.affirm.loan.converter;

import com.affirm.loan.model.Bank;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@RunWith(Parameterized.class)
public class SuccessBankCSVConverterTest extends AbstractGenericCSVConverterTest {
    private Collection<Bank> banks;

    public SuccessBankCSVConverterTest(String input, Bank bank) {
        super(input);
        this.banks = Collections.singleton(bank);
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1,ABC", new Bank(1, "ABC")},
                {"2 ,EFG  ", new Bank(2, "EFG")},
        });
    }

    @Test
    public void testBankConverter(){
        BankCSVConverter converter = new BankCSVConverter(input);
        Assert.assertEquals(converter.convert(), banks);
    }

}
