package com.affirm.loan.model;

import java.util.Optional;
import java.util.OptionalDouble;

/**
 * @author indranil dey
 * Covenant model object related {@link Bank} and {@link Facility}
 */
@SuppressWarnings({"OptionalUsedAsFieldOrParameterType"})
public class Covenant {
    private int bankId;
    private int facilityId;
    //Optional Max likely hood
    private OptionalDouble maxDefaultLikelyHood;
    //Optional Bank State
    private Optional<String> bannedState;
    //Type of Covenant is it max default like hood, banned state
    private CovenantType covenantType;

    private Covenant(int bankId, int facilityId,
                     OptionalDouble maxDefaultLikelyHood,
                     Optional<String> bannedState,CovenantType covenantType) {
        this.bankId = bankId;
        this.facilityId = facilityId;
        this.maxDefaultLikelyHood = maxDefaultLikelyHood;
        this.bannedState = bannedState;
        this.covenantType = covenantType;
    }

    public Covenant(int bankId, int facilityId, String bannedState) {
        this(bankId,facilityId,OptionalDouble.empty(),Optional.of(bannedState),
                CovenantType.BANNED_STATE);
    }

    public Covenant(int bankId, int facilityId, double maxDefaultLikelyHood) {
        this(bankId,facilityId,OptionalDouble.of(maxDefaultLikelyHood),Optional.empty(),
                CovenantType.MAX_DEFAULT_LIKELY_HOOD);
    }

    public int getBankId() {
        return bankId;
    }

    public int getFacilityId() {
        return facilityId;
    }

    public OptionalDouble getMaxDefaultLikelyHood() {
        return maxDefaultLikelyHood;
    }

    public Optional<String> getBannedState() {
        return bannedState;
    }


    public CovenantType getCovenantType() {
        return covenantType;
    }



    public enum CovenantType {
        MAX_DEFAULT_LIKELY_HOOD, BANNED_STATE
    }
}
