package com.affirm.loan.reader;

import com.affirm.loan.converter.CovenantCSVConverter;
import com.affirm.loan.converter.GenericCSVConverter;
import com.affirm.loan.db.BankStorage;
import com.affirm.loan.db.FacilityStorage;
import com.affirm.loan.model.Covenant;

import java.util.Objects;
import java.util.Optional;
import java.util.OptionalDouble;

/**
 * Given a {@link java.util.stream.Stream} of {@link String} it will process the stream and create
 * {@link java.util.concurrent.ConcurrentMap} with Key {@link CovenantPK} ()} and value {@link Covenant}
 * It also take {@link BankStorage} and {@link FacilityStorage} as reference for referential integrity check
 */
public class CovenantStreamProcessor extends AbstractStreamProcessor<CovenantStreamProcessor.CovenantPK, Covenant> {
    private BankStorage bankStorage;
    private FacilityStorage facilityStorage;

    public CovenantStreamProcessor(BankStorage bankStorage, FacilityStorage facilityStorage) {
        this.bankStorage = bankStorage;
        this.facilityStorage = facilityStorage;
    }

    @Override
    protected GenericCSVConverter<Covenant> getConverter(String s) {
        return new CovenantCSVConverter(s,facilityStorage,bankStorage);
    }

    @Override
    protected CovenantPK mappingKey(Covenant object) {
        return new CovenantPK(object.getFacilityId(),object.getCovenantType(),
                object.getMaxDefaultLikelyHood(),object.getBannedState());
    }


    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static class CovenantPK{
        int facilityId;
        Covenant.CovenantType type;
        OptionalDouble maxDefaultLikelyHood;
        Optional<String> bannedState;

        public CovenantPK(int facilityId, Covenant.CovenantType type, OptionalDouble maxDefaultLikelyHood, Optional<String> bannedState) {
            this.facilityId = facilityId;
            this.type = type;
            this.maxDefaultLikelyHood = maxDefaultLikelyHood;
            this.bannedState = bannedState;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CovenantPK that = (CovenantPK) o;
            return facilityId == that.facilityId &&
                    type == that.type &&
                    Objects.equals(maxDefaultLikelyHood, that.maxDefaultLikelyHood) &&
                    Objects.equals(bannedState, that.bannedState);
        }

        @Override
        public int hashCode() {
            return Objects.hash(facilityId, type, maxDefaultLikelyHood, bannedState);
        }

        @Override
        public String toString() {
            return "CovenantPK{" +
                    "facilityId=" + facilityId +
                    ", type=" + type +
                    ", maxDefaultLikelyHood=" + maxDefaultLikelyHood +
                    ", bannedState=" + bannedState +
                    '}';
        }
    }
}
