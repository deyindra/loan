package com.affirm.loan.model;

import com.affirm.loan.ignore.IgnoreCoverage;

import java.util.Objects;

/**
 * @author indranil Dey
 * Bank model object containing ID and Name
 */
public class Bank {
    private final int id;
    private final String bankName;

    public Bank(final int id, final String bankName) {
        this.id = id;
        this.bankName = bankName;
    }

    public int getId() {
        return id;
    }

    @IgnoreCoverage
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return id == bank.id;
    }

    @IgnoreCoverage
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @IgnoreCoverage
    @Override
    public String toString() {
        return "Bank{" +
                "id=" + id +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
