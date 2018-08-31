package com.affirm.loan.converter;

import com.affirm.loan.converter.exception.GenericConvertException;
import com.affirm.loan.db.BankStorage;
import com.affirm.loan.model.Bank;
import com.affirm.loan.model.Facility;

import java.util.Collection;
import java.util.Collections;

/**
 * @author indranil dey
 * Generic Converter for {@link com.affirm.loan.model.Facility} object
 * @see GenericCSVConverter
 */

public class FacilityCSVConverter extends GenericCSVConverter<Facility> {
    private BankStorage bankStorage;

    /**
     *
     * @param str input String
     * @param bankStorage {@link BankStorage} for referential check
     */
    public FacilityCSVConverter(String str, BankStorage bankStorage) {
        super(str);
        this.bankStorage = bankStorage;
    }

    /**
     *
     * @return {@link Collections#singleton(Object)} of {@link Bank}
     * @throws GenericConvertException in case
     */
    @Override
    public Collection<Facility> convert() throws GenericConvertException {
        try {
                int bankId = Integer.parseInt(array[3].trim());
                int facilityId = Integer.parseInt(array[2].trim());
                double interestRate = Double.parseDouble(array[1].trim());
                //Assuming this will be in cents and no decimal points
                long amount = (long)Double.parseDouble(array[0].trim());
                //In case bank Id does not exists
                if(!bankStorage.getMap().containsKey(bankId)){
                    throw new GenericConvertException("Invalid Bank Key");
                }
                //In case interest rate is 0
                if(interestRate<0d || interestRate>1d){
                    throw new GenericConvertException("Invalid Interest rate");
                }
                // In case amount is less than or equal to 0
                if(amount<=0){
                    throw new GenericConvertException("Invalid amount");
                }

                return Collections.singleton(new Facility(facilityId,bankId,interestRate,amount));

        }catch (GenericConvertException ex){
            throw ex;
        }catch (Exception ex){
            throw new GenericConvertException("Error", ex);
        }
    }
}
