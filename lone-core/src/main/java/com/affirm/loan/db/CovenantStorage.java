package com.affirm.loan.db;

import com.affirm.loan.db.exception.DBStorageException;
import com.affirm.loan.model.Covenant;
import com.affirm.loan.reader.CovenantStreamProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import static com.affirm.loan.db.lock.DBLock.READ_LOCK;
import static com.affirm.loan.db.lock.DBLock.WRITE_LOCK;

public class CovenantStorage extends DBStorage<CovenantStreamProcessor.CovenantPK, Covenant> {
    private ConcurrentMap<String, Set<Integer>> bannedStateMap;
    private ConcurrentMap<Integer, Double> maxDefaultLikelihoodMap;

    public CovenantStorage(String file, BankStorage bankStorage, FacilityStorage facilityStorage) {
        super(file, new CovenantStreamProcessor(bankStorage,facilityStorage));
    }


    @Override
    protected void process(Path file) {
        if (map == null) {
            map = new ConcurrentHashMap<>();
        }
        if(bannedStateMap == null){
            bannedStateMap = new ConcurrentHashMap<>();
        }
        if(maxDefaultLikelihoodMap == null){
            maxDefaultLikelihoodMap = new ConcurrentHashMap<>();
        }

        WRITE_LOCK.lock();
        log.info("Processing File " + file.toString());
        try (Stream<String> s = Files.lines(file).skip(1)) {
            streamProcessor.mapping(s, map);
            Set<CovenantStreamProcessor.CovenantPK> set = map.keySet();
            set.forEach(
                  x->{
                     if(x.getType()== Covenant.CovenantType.BANNED_STATE && x.getBannedState().isPresent()){
                         String state = x.getBannedState().get();
                         Set<Integer> facilityIds = bannedStateMap.getOrDefault(state, new TreeSet<>());
                         facilityIds.add(x.getFacilityId());
                         bannedStateMap.put(state,facilityIds);
                     }else if(x.getType() == Covenant.CovenantType.MAX_DEFAULT_LIKELY_HOOD
                             && x.getMaxDefaultLikelyHood().isPresent()){
                         double maxLikeLyHood
                                 = maxDefaultLikelihoodMap.getOrDefault(x.getFacilityId(), Double.MIN_VALUE);

                         double actualMaxLikeLyHood = x.getMaxDefaultLikelyHood().getAsDouble();
                         if(maxLikeLyHood<actualMaxLikeLyHood){
                             maxLikeLyHood = actualMaxLikeLyHood;
                         }
                         maxDefaultLikelihoodMap.put(x.getFacilityId(),maxLikeLyHood);
                     }
                  }
            );

        }catch (IOException ex){
            throw new DBStorageException("Error", ex);
        }finally {
            WRITE_LOCK.unlock();
        }
    }

    public ConcurrentMap<String, Set<Integer>> getBannedStateMap() {
        READ_LOCK.lock();
        try {
            return bannedStateMap;
        }finally {
            READ_LOCK.unlock();
        }
    }

    public ConcurrentMap<Integer, Double> getMaxDefaultLikelihoodMap() {
        READ_LOCK.lock();
        try {
            return maxDefaultLikelihoodMap;
        }finally {
            READ_LOCK.unlock();
        }
    }
}
