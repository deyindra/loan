package com.affirm.loan.db;

import com.affirm.loan.model.Bank;
import com.affirm.loan.reader.BankStreamProcessor;



/**
 * In memory structure for storing {@link Bank} objects
 */
public class BankStorage extends DBStorage<Integer, Bank> {
    /**
     *
     * @param file File path for loading CSV
     */
    public BankStorage(String file) {
        super(file, new BankStreamProcessor());
    }


}
