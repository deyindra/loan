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


/**
 * In memory Structure for Storing {@link Covenant}
 */
public class CovenantStorage extends DBStorage<CovenantStreamProcessor.CovenantPK, Covenant> {
    //Banned StateMap
    private ConcurrentMap<String, Set<Integer>> bannedStateMap;
    //Maximum Default Likely hood Map based on Facility ID
    private ConcurrentMap<Integer, Double> maxDefaultLikelihoodMap;

    public CovenantStorage(String file, BankStorage bankStorage, FacilityStorage facilityStorage) {
        super(file, new CovenantStreamProcessor(bankStorage,facilityStorage));
    }


    @Override
    protected synchronized void process(Path file) {
        map = new ConcurrentHashMap<>();
        bannedStateMap = new ConcurrentHashMap<>();
        maxDefaultLikelihoodMap = new ConcurrentHashMap<>();
        log.info("Processing File " + file.toString());
        try (Stream<String> s = Files.lines(file).skip(1)) {
            streamProcessor.mapping(s, map);
            Set<CovenantStreamProcessor.CovenantPK> set = map.keySet();
            set.forEach(
                  x->{
                     if(x.getBannedState().isPresent()){
                         String state = x.getBannedState().get();
                         Set<Integer> facilityIds = bannedStateMap.getOrDefault(state, new TreeSet<>());
                         facilityIds.add(x.getFacilityId());
                         bannedStateMap.put(state,facilityIds);
                     }

                     if(x.getMaxDefaultLikelyHood().isPresent()){
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
        }
    }

    /**
     *
     * @return Concurrent view of Banned State vs Facility IDS
     */
    public synchronized ConcurrentMap<String, Set<Integer>> getBannedStateMap() {
        return bannedStateMap;
    }

    /**
     *
     * @return Concurrent view of Facility ID and Max Default likely hood map
     * Please note for a same facility ID if we get 2 max Default likely hood, it will pick the
     * maximum one
     */
    public synchronized ConcurrentMap<Integer, Double> getMaxDefaultLikelihoodMap() {
        return maxDefaultLikelihoodMap;
    }
}
