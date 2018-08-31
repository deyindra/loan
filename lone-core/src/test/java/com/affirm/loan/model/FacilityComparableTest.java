package com.affirm.loan.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FacilityComparableTest {
    @Test
    public void testFacilityComparable(){
        Set<Facility> set = new TreeSet<>();

        set.add(new Facility(1,1,0.9,5000));
        set.add(new Facility(2,1,0.9,5000));
        set.add(new Facility(3,1,0.2,15000));
        set.add(new Facility(4,1,0.2,35000));
        set.add(new Facility(5,1,0.1,9000));

        List<Facility> inputList = new ArrayList<>(set);

        List<Facility> list = new ArrayList<>();

        list.add(new Facility(5,1,0.1,9000));
        list.add(new Facility(4,1,0.2,35000));
        list.add(new Facility(3,1,0.2,15000));
        list.add(new Facility(1,1,0.9,5000));
        list.add(new Facility(2,1,0.9,5000));

        Assert.assertEquals(inputList,list);

    }
}
