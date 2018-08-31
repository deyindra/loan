package com.affirm.loan.converter;

import com.affirm.loan.model.Bank;
import com.affirm.loan.model.Loan;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@RunWith(Parameterized.class)
public class SuccessLoanCSVConverterTest extends AbstractGenericCSVConverterTest {
    private Collection<Loan> loans;

    public SuccessLoanCSVConverterTest(String input, Loan loan) {
        super(input);
        this.loans = Collections.singleton(loan);
    }


    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"1,100,0.2,0.9,CA", new Loan(1, 100,0.2,0.9,"CA")},
        });
    }

    @Test
    public void testBankConverter(){
        LoanCSVConverter converter = new LoanCSVConverter(input);
        Assert.assertEquals(converter.convert(), loans);
    }

}
