package com.affirm.loan.converter;

import com.affirm.loan.converter.exception.GenericConvertException;
import com.affirm.loan.model.Bank;
import com.affirm.loan.model.Loan;

import java.util.Collection;
import java.util.Collections;

/**
 * @author indranil dey
 * Generic Converter for {@link Loan} object
 * @see GenericCSVConverter
 */
public class LoanCSVConverter extends GenericCSVConverter<Loan> {
    public LoanCSVConverter(String str) {
        super(str);
    }

    @Override
    public Collection<Loan> convert() throws GenericConvertException {
        try {
            int loanId = Integer.parseInt(array[0].trim());
            long amount = Long.parseLong(array[1].trim());
            double interestRate = Double.parseDouble(array[2].trim());
            double defaultLikelyHood = Double.parseDouble(array[3].trim());
            String state = (array.length==5) ? array[4].trim(): "";
            //Throw GenericConvertException in case amount is less than 0
            if (amount <= 0) {
                throw new GenericConvertException("Invalid Amount " + amount);
            }
            //Throw GenericConvertException in case interestRate is less than 0 or greater than 1
            if (interestRate < 0.0d || interestRate > 1.0d) {
                throw new GenericConvertException("Invalid Interest Rate " + interestRate);
            }
            //Throw GenericConvertException in case defaultLikelyHood is less than 0 or greater than 1
            if (defaultLikelyHood < 0.0d || defaultLikelyHood > 1.0d) {
                throw new GenericConvertException("Invalid default likely hood rate " + defaultLikelyHood);
            }
            //Throw GenericConvertException in case state is blank
            if (("").equals(state)) {
                throw new GenericConvertException("Invalid State");
            }
            return Collections.singleton(new Loan(loanId,amount,interestRate,defaultLikelyHood,state));
        }catch (GenericConvertException ex){
            throw ex;
        }catch (Exception ex){
            throw new GenericConvertException("Error", ex);
        }

    }
}
