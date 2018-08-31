package com.affirm.loan.reader;

import com.affirm.loan.converter.FacilityCSVConverter;
import com.affirm.loan.converter.GenericCSVConverter;
import com.affirm.loan.db.BankStorage;
import com.affirm.loan.model.Facility;

/**
 * Given a {@link java.util.stream.Stream} of {@link String} it will process the stream and create
 * {@link java.util.concurrent.ConcurrentMap} with Key {@link Facility#getFacilityId()} ()} and value {@link Facility}
 * It also take {@link BankStorage} as reference for referential integrity check
 */
public class FacilityStreamProcessor extends AbstractStreamProcessor<Integer, Facility> {
    private BankStorage storage;

    public FacilityStreamProcessor(BankStorage storage) {
        this.storage = storage;
    }

    @Override
    protected GenericCSVConverter<Facility> getConverter(String s) {
        return new FacilityCSVConverter(s,storage);
    }

    @Override
    protected Integer mappingKey(Facility object) {
        return object.getFacilityId();
    }
}
