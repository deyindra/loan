package com.affirm.loan.reader;

import com.affirm.loan.converter.GenericCSVConverter;
import com.affirm.loan.reader.exception.StreamProcessorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

/**
 * @author indranil dey
 * Genetic Stream Processor based on the Passed {@link GenericCSVConverter}
 * @param <E> Any type Group key
 * @param <T> any object
 */
public abstract class AbstractStreamProcessor<E,T> {
    protected static final Logger log = LoggerFactory.getLogger(AbstractStreamProcessor.class);

    /**
     *
     * @param s any String from file
     * @return {@link GenericCSVConverter}
     */
    protected abstract GenericCSVConverter<T> getConverter(String s);

    /**
     *
     * @param object Underlyinig Object for getting the group key
     * @return return group Key
     */
    protected abstract E mappingKey(T object);

    /**
     *
     * @param s  {@link Stream} of inputs
     */
    public void mapping(Stream<String> s, ConcurrentMap<E, T> inputMap){
        Stream<String> finalStream= s.isParallel() ? s : s.parallel();

        finalStream.forEach(
                x -> {
                    try {
                        Collection<T> collection = getConverter(x).convert();
                        for (T object : collection) {
                            E key = mappingKey(object);
                            inputMap.put(key, object);
                        }
                    }catch (Exception e){
                        log.error("Error", e);
                        throw new StreamProcessorException("Error", e);
                    }
                }
        );
    }

}
