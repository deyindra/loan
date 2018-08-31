package com.affirm.loan.db;

import com.affirm.loan.db.exception.DBStorageException;
import com.affirm.loan.model.Facility;
import com.affirm.loan.reader.FacilityStreamProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Stream;


/**
 * In memory structure for storing {@link Facility} objects
 */
public class FacilityStorage extends DBStorage<Integer, Facility> {
    /**
     * It maintains total Sorted View of Facility
     */
    private ConcurrentSkipListSet<Facility> sortedFacilitySet;
    private ConcurrentMap<Integer, ConcurrentSkipListSet<Facility>> groupByBank;

    /**
     *
     * @param file File path for loading CSV
     */
    public FacilityStorage(String file, BankStorage storage) {
        super(file, new FacilityStreamProcessor(storage));
    }

    @Override
    public synchronized void process(Path file) {
        if (map == null) {
            map = new ConcurrentHashMap<>();
        }
        if(sortedFacilitySet == null){
            sortedFacilitySet = new ConcurrentSkipListSet<>();
        }
        if(groupByBank == null){
            groupByBank = new ConcurrentHashMap<>();
        }
        log.info("Processing File " + file.toString());
        try (Stream<String> s = Files.lines(file).skip(1)) {
            streamProcessor.mapping(s, map);
            sortedFacilitySet.addAll(map.values());
            for(Facility f:sortedFacilitySet){
                int bankId = f.getBankId();
                ConcurrentSkipListSet<Facility> set = groupByBank.getOrDefault(bankId, new ConcurrentSkipListSet<>());
                set.add(f);
                groupByBank.put(bankId,set);
            }
        }catch (IOException ex){
            throw new DBStorageException("Error", ex);
        }
    }


    @Override
    public synchronized Map<Integer, Facility> getMap() {
        return super.getMap();
    }

    /**
     *
     * @return Concurrent Sorted view of {@link Facility}
     */
    public synchronized ConcurrentSkipListSet<Facility> getSortedFacilityMap() {
        return sortedFacilitySet;
    }

    /**
     *
     * @return sorted Set of Facility group by bank
     */
    public synchronized ConcurrentMap<Integer, ConcurrentSkipListSet<Facility>> getGroupByBank() {
        return groupByBank;
    }
}
