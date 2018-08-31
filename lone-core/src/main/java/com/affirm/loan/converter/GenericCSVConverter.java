package com.affirm.loan.converter;

import com.affirm.loan.converter.exception.GenericConvertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author indranil dey
 * Generic CSV converter
 * @param <T>
 * @see BankCSVConverter
 */
public abstract class GenericCSVConverter<T> implements Converter<T> {
    protected String[] array;
    protected static final Logger log = LoggerFactory.getLogger(GenericCSVConverter.class);
    /**
     * Take an input String and convert the same to String Array based on delimiter
     * @param str input String
     * @param regex any regular expression
     * @throws GenericConvertException in case String in null or empty
     */
    protected GenericCSVConverter(String str, String regex) {
        try {
            if (str == null || ("").equals(str.trim())) {
                throw new GenericConvertException("Invalid Input");
            }
            str = str.trim();
            this.array = str.split(regex);
        }catch (Exception ex){
            throw new GenericConvertException("Error", ex);
        }
    }

    /**
     * Split the String based on Comma ","
     * @param str input String
     */
    protected GenericCSVConverter(String str) {
        this(str, ",");
    }
}
