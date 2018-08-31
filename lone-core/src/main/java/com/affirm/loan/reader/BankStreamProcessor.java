package com.affirm.loan.reader;

import com.affirm.loan.converter.BankCSVConverter;
import com.affirm.loan.converter.GenericCSVConverter;
import com.affirm.loan.model.Bank;


/**
 * Given a {@link java.util.stream.Stream} of {@link String} it will process the stream and create
 * {@link java.util.concurrent.ConcurrentMap} with Key {@link Bank#getId()} and value {@link Bank}
 */
public class BankStreamProcessor extends AbstractStreamProcessor<Integer, Bank> {
    @Override
    public Integer mappingKey(Bank object) {
        return object.getId();
    }

    @Override
    public GenericCSVConverter<Bank> getConverter(String s) {
        return new BankCSVConverter(s);
    }

}
