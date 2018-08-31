package com.affirm.loan.converter;

import com.affirm.loan.converter.exception.GenericConvertException;
import com.affirm.loan.model.Bank;

import java.util.Collection;
import java.util.Collections;

/**
 * @author indranil dey
 * Generic Converter for {@link Bank} object
 * @see GenericCSVConverter
 */

public class BankCSVConverter extends GenericCSVConverter<Bank> {
    public BankCSVConverter(String str) {
        super(str);
    }

    /**
     *
     * @return {@link Collections#singleton(Object)} of {@link Bank}
     * @throws GenericConvertException in case
     */
    @Override
    public Collection<Bank> convert() throws GenericConvertException {
        try {
            return Collections.singleton(new Bank(Integer.parseInt(array[0].trim()), array[1].trim()));
        }catch (Exception ex){
            throw new GenericConvertException("Error", ex);
        }
    }
}
