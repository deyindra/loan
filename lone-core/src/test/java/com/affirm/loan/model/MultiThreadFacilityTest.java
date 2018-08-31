package com.affirm.loan.model;

import org.junit.Assert;
import org.junit.Test;

public class MultiThreadFacilityTest {

    @Test
    public void multiThreadAmountUpdateTest() throws InterruptedException {
        Facility f = new Facility(1,1,0.9,2000);
        for(int i=1;i<=10;i++){
            long factory = (i % 2)==0 ? i : -i;
            Thread updateAmountRunnable = new Thread(new UpdateAmountRunnable(f,factory*100));
            updateAmountRunnable.start();
            updateAmountRunnable.join();
        }
        Assert.assertEquals(f.getAmount(),2500L);
    }


    private static class UpdateAmountRunnable implements Runnable{
        private Facility facility;
        private final long updatedAmount;

        private UpdateAmountRunnable(Facility facility, long updatedAmount) {
            this.facility = facility;
            this.updatedAmount = updatedAmount;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(2000L);
                this.facility.updateAmount(updatedAmount);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
