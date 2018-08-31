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

import static com.affirm.loan.db.lock.DBLock.*;

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
    public void process(Path file) {
        if (map == null) {
            map = new ConcurrentHashMap<>();
        }
        if(sortedFacilitySet == null){
            sortedFacilitySet = new ConcurrentSkipListSet<>();
        }
        if(groupByBank == null){
            groupByBank = new ConcurrentHashMap<>();
        }

        WRITE_LOCK.lock();
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
        }finally {
            WRITE_LOCK.unlock();
        }
    }


    @Override
    public Map<Integer, Facility> getMap() {
        READ_LOCK.lock();
        try {
            return super.getMap();
        }finally {
            READ_LOCK.unlock();
        }
    }

    /**
     *
     * @return Concurrent Sorted view of {@link Facility}
     */
    public ConcurrentSkipListSet<Facility> getSortedFacilityMap() {
        READ_LOCK.lock();
        try {
            return sortedFacilitySet;
        }finally {
            READ_LOCK.unlock();
        }
    }

    /**
     *
     * @return sorted Set of Facility group by bank
     */
    public ConcurrentMap<Integer, ConcurrentSkipListSet<Facility>> getGroupByBank() {
        READ_LOCK.lock();
        try {
            return groupByBank;
        }finally {
            READ_LOCK.unlock();
        }
    }
}
