package com.affirm.loan.converter;

import com.affirm.loan.converter.exception.GenericConvertException;
import com.affirm.loan.db.BankStorage;
import com.affirm.loan.db.FacilityStorage;
import com.affirm.loan.model.Covenant;
import com.affirm.loan.model.Facility;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * @author indranil dey
 * Generic Converter for {@link com.affirm.loan.model.Covenant} object
 * @see GenericCSVConverter
 */
public class CovenantCSVConverter extends GenericCSVConverter<Covenant> {
    private FacilityStorage facilityStorage;
    private BankStorage bankStorage;

    public CovenantCSVConverter(String str, FacilityStorage facilityStorage, BankStorage bankStorage) {
        super(str);
        this.facilityStorage = facilityStorage;
        this.bankStorage = bankStorage;
    }

    @Override
    public Collection<Covenant> convert() throws GenericConvertException {
        try {
            String facilityId = array[0].trim();
            String maxDefaultLikelyHood = array[1].trim();
            int bankId = Integer.parseInt(array[2].trim());
            String bannedState = (array.length==4) ? array[3].trim():"";
            List<Covenant> list = new ArrayList<>();
            //throw exception if bank id is not present
            if (!bankStorage.getMap().containsKey(bankId)) {
                throw new GenericConvertException("Invalid Bank ID");
            }

            if(("").equals(maxDefaultLikelyHood) && ("").equals(bannedState)){
                throw new GenericConvertException("both max default likely hood and banned state can not be optional");
            }

            //Check validity of max default likely hood
            OptionalDouble dblMaxDefaultLikelyHood = OptionalDouble.empty();
            if (!("").equals(maxDefaultLikelyHood)) {
                double d = Double.parseDouble(maxDefaultLikelyHood);
                if (d < 0.0d || d > 1.0d) {
                    throw new GenericConvertException("Invalid max default likely hood " + d);
                } else {
                    dblMaxDefaultLikelyHood = OptionalDouble.of(d);
                }
            }

            ConcurrentSkipListSet<Facility> facilities = facilityStorage.getGroupByBank().get(bankId);

            //Check validity for facility
            Set<Integer> facilityIds;
            //In case facility Id is blank get all facility ids for the given bank
            // else the specific facility id if that exists for a given bank
            if (!("").equals(facilityId)) {
                int intFacilityId = Integer.parseInt(facilityId);
                Facility f = facilityStorage.getMap().get(intFacilityId);
                //Facility exists but does belong to right bank??
                if (f != null) {
                    if (f.getBankId() != bankId) {
                        throw new GenericConvertException("Given Facility " + facilityId + "  does not belongs to bank " + bankId);
                    } else {
                        facilityIds = Collections.singleton(intFacilityId);
                    }
                } else {
                    throw new GenericConvertException("Invalid facility id " + intFacilityId);
                }
            } else {
                //get all facilities for the bank
                log.info("No Facility id exists...hence getting all facility Id");
                facilityIds = new HashSet<>();
                for (Facility f : facilities) {
                    facilityIds.add(f.getFacilityId());
                }
            }
            //populate covenant
            for (int id : facilityIds) {
                if (dblMaxDefaultLikelyHood.isPresent()) {
                    list.add(new Covenant(bankId, id, dblMaxDefaultLikelyHood.getAsDouble()));
                }

                if (!("").equals(bannedState)) {
                    list.add(new Covenant(bankId, id, bannedState));
                }
            }
            return list;
        }catch (GenericConvertException ex){
            throw ex;
        }catch (Exception ex){
            throw new GenericConvertException("Error", ex);
        }

     }
}
