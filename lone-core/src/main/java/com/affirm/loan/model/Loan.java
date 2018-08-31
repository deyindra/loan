package com.affirm.loan.model;

import com.affirm.loan.ignore.IgnoreCoverage;

import java.util.Objects;

public class Loan {
    private final int loadId;
    private final long amount;
    private final double interestRate;
    private final double defaultLikelyHood;
    private final String state;

    public Loan(int loadId, long amount, double interestRate,
                double defaultLikelyHood, String state) {
        this.loadId = loadId;
        this.amount = amount;
        this.interestRate = interestRate;
        this.defaultLikelyHood = defaultLikelyHood;
        this.state = state;
    }

    public int getLoadId() {
        return loadId;
    }

    public long getAmount() {
        return amount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getDefaultLikelyHood() {
        return defaultLikelyHood;
    }

    public String getState() {
        return state;
    }

    @IgnoreCoverage
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return loadId == loan.loadId;
    }

    @IgnoreCoverage
    @Override
    public int hashCode() {
        return Objects.hash(loadId);
    }

    @IgnoreCoverage
    @Override
    public String toString() {
        return "Loan{" +
                "loadId=" + loadId +
                ", amount=" + amount +
                ", interestRate=" + interestRate +
                ", defaultLikelyHood=" + defaultLikelyHood +
                ", state='" + state + '\'' +
                '}';
    }
}
