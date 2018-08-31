package com.affirm.loan.converter;

import com.affirm.loan.converter.exception.GenericConvertException;

import java.util.Collection;

/**
 * @author indranil dey
 * An interface which will convert to any generic object
 * @param <T> Any type
 * @see GenericCSVConverter
 */
public interface Converter<T> {
    /**
     *
     * @return an object of type T
     * @throws GenericConvertException in case of any conversion error
     */
    Collection<T> convert() throws GenericConvertException;
}
