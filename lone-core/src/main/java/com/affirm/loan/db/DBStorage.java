package com.affirm.loan.db;

import com.affirm.loan.db.exception.DBStorageException;
import com.affirm.loan.reader.AbstractStreamProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * In memory storage representation (e.g. DB) for all the entity, Bank, Facility, Covenant etc.
 * @see BankStorage
 */
public abstract class DBStorage<E,T> {
    protected static final Logger log = LoggerFactory.getLogger(DBStorage.class);
    protected AbstractStreamProcessor<E,T> streamProcessor;
    protected ConcurrentMap<E,T> map;
    /**
     *
     * @param file CSV file name which will be used to load the entity in memory
     * @throws DBStorageException in case file name is empty or null or not exists or it is a folder
     */
    protected DBStorage(String file, AbstractStreamProcessor<E,T> streamProcessor) {
        try {
            //throws DBStorageException in case file is null or empty
            if (file == null || ("").equals(file.trim())) {
                throw new DBStorageException("Invalid File Path");
            }
            Path p = Paths.get(file.trim());
            this.streamProcessor = streamProcessor;
            process(p);
        }catch (Exception ex){
            throw new DBStorageException("Error", ex);
        }
    }

    /**
     *
     * @param file Process Records from inputs and put them in {@link ConcurrentMap}
     */
    protected void process(Path file){
        if (map == null) {
            map = new ConcurrentHashMap<>();
        }
        log.info("Processing File " + file.toString());
        try (Stream<String> s = Files.lines(file).skip(1)) {
            streamProcessor.mapping(s, map);
        }catch (IOException ex){
            throw new DBStorageException("Error", ex);
        }
    }

    /**
     *
     * @return Map of Primary Key and Underlying Object
     */
    public Map<E, T> getMap() {
        return Collections.unmodifiableMap(map);
    }

}
