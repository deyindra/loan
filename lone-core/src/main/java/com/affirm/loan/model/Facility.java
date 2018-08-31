package com.affirm.loan.model;

import com.affirm.loan.ignore.IgnoreCoverage;

import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author indranil Dey
 * Facilty model Object associated with {@link Bank}
 */
public class Facility implements Comparable<Facility>{
    private final int facilityId;
    private final int bankId;
    private final double interestRate;
    private final LongAdder amount;

    public Facility(final int facilityId, final int bankId, final double interestRate, final long amount) {
        this.facilityId = facilityId;
        this.bankId = bankId;
        this.interestRate = interestRate;
        this.amount = new LongAdder();
        this.amount.add(amount);
    }

    public int getFacilityId() {
        return facilityId;
    }

    public int getBankId() {
        return bankId;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public long getAmount() {
        return amount.longValue();
    }

    public void updateAmount(final long amount){
        this.amount.add(amount);
    }

    @IgnoreCoverage
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        return facilityId == facility.facilityId;
    }

    @IgnoreCoverage
    @Override
    public int hashCode() {
        return Objects.hash(facilityId);
    }

    /**
     * Compare two facilities
     * @param o Input Facility
     * @return 0, negative number or positive number based on lowest interest rate, highest funds
     * (if interest rates are same) and lowest facility id value (if both funds are same)
     */
    @Override
    public int compareTo(Facility o) {
        if(this==o){
            return 0;
        }else{
            int returnType = Double.compare(this.interestRate, o.interestRate);
            if(returnType==0){
                returnType = -Long.compare(this.amount.longValue(),o.amount.longValue());
                if(returnType == 0){
                    returnType = Integer.compare(this.facilityId,o.facilityId);
                }
            }
            return returnType;
        }
    }

    @IgnoreCoverage
    @Override
    public String toString() {
        return "Facility{" +
                "facilityId=" + facilityId +
                ", bankId=" + bankId +
                ", interestRate=" + interestRate +
                ", amount=" + amount +
                '}';
    }
}
